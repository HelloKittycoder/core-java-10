package set;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 * 9.2.3 散列集
 * This program uses a set to print all unique words in System.in.
 *
 * Created by shucheng on 10/25/2020 3:24 PM
 */
public class SetTest {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("gutenberg/alice30.txt");
        test(Files.newInputStream(path));
    }

    public static void test(InputStream inputStream) {
        Set<String> words = new HashSet<>(); // HashSet implements Set
        long totalTime = 0;

        try (Scanner in = new Scanner(inputStream)) {
            while (in.hasNext()) {
                String word = in.next();
                long callTime = System.currentTimeMillis();
                words.add(word);
                callTime = System.currentTimeMillis() - callTime;
                totalTime += callTime;
            }
        }

        Iterator<String> iter = words.iterator();
        for (int i = 1; i <= 20 && iter.hasNext(); i++) {
            System.out.print(iter.next() + " ");
        }
        System.out.println("...");
        System.out.println(words.size() + " distinct words. " + totalTime + " milliseconds.");
    }
}
