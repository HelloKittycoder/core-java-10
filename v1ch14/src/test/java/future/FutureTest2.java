package future;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * FutureTest的单线程版本
 * Created by shucheng on 2022/11/21 8:59
 */
public class FutureTest2 {

    public static int count = 0;

    public static void main(String[] args) {
        /**
         * D:\Code\shucheng\IntelliJIdeaProjects\gitProjects\jdk1.8-source-study\src
         * volatile
         * 2686ms
         */
        String directoryPath = "D:\\Code\\shucheng\\IntelliJIdeaProjects\\gitProjects\\jdk1.8-source-study\\src";
        String keyword = "volatile";

        long start = System.currentTimeMillis();
        File directory = new File(directoryPath);
        search(directory, keyword);
        System.out.println(count); // 168
        System.out.println("耗时：" + (System.currentTimeMillis() - start) + "ms");
    }

    private static void search(File file, String keyword) {
        if (file.isDirectory()) {
            // 如果是目录
            for (File f : file.listFiles()) {
                search(f, keyword);
            }
        } else {
            // 如果是文件
            boolean found = doSearch(file, keyword);
            if (found) {
                count++;
            }
        }
    }

    private static boolean doSearch(File file, String keyword) {
        try {
            boolean found = false;
            try (Scanner scanner = new Scanner(file, "UTF-8")) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.contains(keyword)) {
                        found = true;
                        break;
                    }
                }
            }
            return found;
        } catch (IOException e) {
            return false;
        }
    }
}
