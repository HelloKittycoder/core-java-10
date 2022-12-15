package memoryMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Function;
import java.util.zip.CRC32;

/**
 * 2.6.1 内存映射文件的性能
 * Created by shucheng on 2020/9/24 21:14
 */
public class MemoryMapTest {

    private static Logger LOGGER = LoggerFactory.getLogger(MemoryMapTest.class);

    // 每次读取1个字节
    @Test
    public void test() {
        String java_home = System.getProperty("java.home");
        Path path = Paths.get(java_home).resolve("../javafx-src.zip"); // 4.96M
        calculateTime("Input Stream:", path, MemoryMapTest::checksumInputStream); // 8.5s
        calculateTime("Buffered Input Stream:", path, MemoryMapTest::checksumBufferedInputStream); // 26ms
        calculateTime("Random Access File:", path, MemoryMapTest::checksumRandomAccessFile); // 10.8s
        calculateTime("Mapped File:", path, MemoryMapTest::checksumMappedFile); // 22ms
    }

    public static long checksumInputStream(Path filePath) {
        try (InputStream in = Files.newInputStream(filePath)) {
            CRC32 crc = new CRC32();

            int c;
            while ((c = in.read()) != -1) {
                crc.update(c);
            }
            return crc.getValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static long checksumBufferedInputStream(Path filePath) {
        try (InputStream in = new BufferedInputStream(Files.newInputStream(filePath))) {
            CRC32 crc = new CRC32();

            int c;
            while ((c = in.read()) != -1) {
                crc.update(c);
            }
            return crc.getValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static long checksumRandomAccessFile(Path filePath) {
        try (RandomAccessFile file = new RandomAccessFile(filePath.toFile(), "r")) {
            long length = file.length();
            CRC32 crc = new CRC32();

            for (long p = 0; p < length; p++) {
                file.seek(p);
                int c = file.readByte();
                crc.update(c);
            }
            return crc.getValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static long checksumMappedFile(Path filePath) {
        try (FileChannel channel = FileChannel.open(filePath)) {
            CRC32 crc = new CRC32();
            int length = (int) channel.size();
            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, length);

            for (int p = 0; p < length; p++) {
                int c = buffer.get(p);
                crc.update(c);
            }
            return crc.getValue();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void calculateTime(String title, Path file, Function<Path, Long> function) {
        LOGGER.info(title);
        long start = System.currentTimeMillis();
        long crcValue = function.apply(file);
        long end = System.currentTimeMillis();
        LOGGER.info(Long.toHexString(crcValue));
        LOGGER.info("{} milliseconds", end - start);
    }
}
