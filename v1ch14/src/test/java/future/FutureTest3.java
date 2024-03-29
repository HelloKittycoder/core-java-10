package future;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * FutureTest的线程池版本
 * Created by shucheng on 2020/11/2 8:35
 */
public class FutureTest3 {

    private static ExecutorService executorService = Executors.newFixedThreadPool(100);

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            /**
             * D:\Code\shucheng\IntelliJIdeaProjects\gitProjects\jdk1.8-source-study\src
             * volatile
             * 1035ms
             */
            System.out.print("Enter base directory (e.g. /opt/jdk1.8.0/src): ");
            String directory = in.nextLine();
            System.out.print("Enter keyword (e.g. volatile): ");
            String keyword = in.nextLine();

            long start = System.currentTimeMillis();
            MatchCounter counter = new MatchCounter(new File(directory), keyword);
            FutureTask<Integer> task = new FutureTask<>(counter);
            /*Thread t = new Thread(task);
            t.start();*/
            executorService.submit(task);
            try {
                System.out.println(task.get() + "matching files.");
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {}
            executorService.shutdown();
            System.out.println("耗时：" + (System.currentTimeMillis() - start) + "ms");
        }
    }

    /**
     * This task counts the files in a directory and its subdirectories that contain a given keyword.
     */
    static class MatchCounter implements Callable<Integer> {

        private File directory;
        private String keyword;

        /**
         * Constructs a MatchCounter
         * @param directory the directory in which to start the search
         * @param keyword the keyword to look for
         */
        public MatchCounter(File directory, String keyword) {
            this.directory = directory;
            this.keyword = keyword;
        }

        @Override
        public Integer call() {
            int count = 0;
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            try {
                for (File file : files) {
                    if (file.isDirectory()) {
                        MatchCounter counter = new MatchCounter(file, keyword);
                        FutureTask<Integer> task = new FutureTask<>(counter);
                        results.add(task);
                        executorService.submit(task);
                    } else {
                        if (search(file)) count++;
                    }
                }

                for (Future<Integer> result : results) {
                    try {
                        count += result.get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            } catch (InterruptedException e) {}
            return count;
        }

        /**
         * Searches a file for given keyword.
         * @param file the file to search
         * @return true if the keyword is contained in the file
         */
        public boolean search(File file) {
            try {
                try (Scanner in = new Scanner(file, "UTF-8")) {
                    boolean found = false;
                    while (!found && in.hasNextLine()) {
                        String line = in.nextLine();
                        if (line.contains(keyword)) found = true;
                    }
                    return found;
                }
            } catch (IOException e) {
                return false;
            }
        }
    }
}

