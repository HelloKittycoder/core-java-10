package memoryMap;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
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
public class MemoryMapTest2 {

    private static Logger LOGGER = LoggerFactory.getLogger(MemoryMapTest2.class);
    public static int BLOCK_SIZE = 1024;

    // 每次读取1024个字节
    @Test
    public void test() {
        String java_home = System.getProperty("java.home");
        Path path = Paths.get(java_home).resolve("../javafx-src.zip"); // 4.96M
        calculateTime("Input Stream:", path, MemoryMapTest2::checksumInputStream); // 18ms
        calculateTime("Buffered Input Stream::", path, MemoryMapTest2::checksumBufferedInputStream); // 4ms
        calculateTime("Random Access File:", path, MemoryMapTest2::checksumRandomAccessFile); // 11.5s
        calculateTime("Mapped File:", path, MemoryMapTest2::checksumMappedFile); // 3ms
    }

    public static long checksumInputStream(Path filePath) {
        try (InputStream in = Files.newInputStream(filePath)) {
            CRC32 crc = new CRC32();

            byte[] bytes = new byte[BLOCK_SIZE];
            int n;
            while ((n = in.read(bytes)) != -1) {
                crc.update(bytes, 0, n);
            }
            return crc.getValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static long checksumBufferedInputStream(Path filePath) {
        try (InputStream in = new BufferedInputStream(Files.newInputStream(filePath))) {
            CRC32 crc = new CRC32();

            byte[] bytes = new byte[BLOCK_SIZE];
            int n;
            while ((n = in.read(bytes)) != -1) {
                crc.update(bytes, 0, n);
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

            byte[] bytes = new byte[BLOCK_SIZE];
            for (long p = 0; p < length; p += BLOCK_SIZE) {
                file.seek(p);
                int n = file.read(bytes);
                crc.update(bytes, 0, n);
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

            byte[] bytes = new byte[BLOCK_SIZE];
            /**
             * 思考：为什么p += BLOCK_SIZE写成p++时，下面会报java.nio.BufferUnderflowException的异常
             * 仔细看下每次buffer中的pos，以及n的值
             *
             * 提示：n表示需要从buffer中读取的字节数量，如果buffer中的数量达不到n，
             * 就会报java.nio.BufferUnderflowException的异常
             */
            for (int p = 0; p < length; p += BLOCK_SIZE) {
                int n = Math.min(BLOCK_SIZE, length - p);
                buffer.get(bytes, 0, n);
                /* 这里只是为了调试找错，才写成这样
                try {
                    buffer.get(bytes, 0, n);
                    System.out.println("buffer==" + buffer + "********n==" + n + "********p==" + p);
                } catch (Exception e) {
                    // System.out.println("buffer==" + buffer + "********n==" + n + "********p==" + p);
                    throw new RuntimeException(e);
                }*/
                crc.update(bytes, 0, n);
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
