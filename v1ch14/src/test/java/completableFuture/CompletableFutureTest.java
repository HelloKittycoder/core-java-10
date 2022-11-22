package completableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.function.*;

/**
 * CompletableFuture的基本使用（网上找的例子）
 * 参考链接：https://blog.csdn.net/sermonlizhi/article/details/123356877
 * Created by shucheng on 2022/11/22 7:20
 */
public class CompletableFutureTest {

    public static final Logger LOGGER = LoggerFactory.getLogger(CompletableFutureTest.class);

    public static void main(String[] args) throws Exception {
        /*test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
        test8();
        test9();
        test10();
        test11();
        test12();*/
        test13();
    }

    /**
     * 测试runAsync和supplyAsync方法
     * runAsync：创建一个异步操作，无参无返回值
     * supplyAsync：创建一个异步操作，无参有返回值
     */
    public static void test1() throws Exception {
        LOGGER.info("进入test1方法");
        Runnable runnable = () -> {
            // System.out.println("无返回结果异步任务");
            LOGGER.info("无返回结果异步任务");
        };
        CompletableFuture.runAsync(runnable);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            LOGGER.info("有返回值的异步任务 enter");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("有返回值的异步任务 leave");
            return "Hello Word";
        });

        // 这里会阻塞调用get的线程，直接future中的所有计算完成
        String s = future.get();
        System.out.println("获取返回值为：" + s);
    }

    /**
     * 测试whenComplete和exceptionally
     * whenComplete：当CompletableFuture的计算完成时，执行特定的action
     * exceptionally：当CompletableFuture的计算抛异常时，执行特定的action
     */
    public static void test2() throws Exception {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            LOGGER.info("CompletableFuture.supplyAsync enter");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (new Random().nextInt(10) % 2 == 0) {
                int i = 1 / 0;
            }

            LOGGER.info("CompletableFuture.supplyAsync leave");
            return "test";
        });

        future.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                LOGGER.info("监听到CompletableFuture.supplyAsync执行完成，结果为：{}", s);
            }
        });

        future.exceptionally(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable t) {
                LOGGER.error("执行失败，错误信息为：{}", t.getMessage());
                return "异步任务执行出错了";
            }
        });

        LOGGER.info("开始调用future.get");
        String s = future.get();
        LOGGER.info("获取到异步执行返回值：{}", s);
    }

    /**
     * 测试thenApply
     * thenApply接收一个函数作为参数，使用该函数处理上一个CompletableFuture调用的结果，并返回一个具有处理结果的Future
     * @throws Exception
     */
    public static void test3() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int result = 100;
            LOGGER.info("第1次运算 start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("第1次运算 end，result：{}", result);
            return result;
        }).thenApply(num -> {
            LOGGER.info("第2次运算 start，取到第1次运算的结果：{}", num);
            int result = num * 3;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("第2次运算 end，result：{}", result);
            return result;
        });

        Integer result = future.get();
        LOGGER.info("最终运算结果为：{}", result);
    }

    /**
     * 测试thenCompose
     * thenCompose的参数为一个返回CompletableFuture实例的函数，该函数的参数是先前计算步骤的结果。
     * @throws Exception
     */
    public static void test4() throws Exception {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int result = 100;
            LOGGER.info("第1次运算 start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("第1次运算 end，result：{}", result);
            return result;
        }).thenCompose(new Function<Integer, CompletionStage<Integer>>() {
            @Override
            public CompletionStage<Integer> apply(Integer num) {
                return CompletableFuture.supplyAsync(new Supplier<Integer>() {
                    @Override
                    public Integer get() {
                        LOGGER.info("第2次运算 start，取到第1次运算的结果：{}", num);
                        int result = num * 3;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        LOGGER.info("第2次运算 end，result：{}", result);
                        return result;
                    }
                });
            }
        });

        Integer result = future.get();
        LOGGER.info("最终运算结果为：{}", result);
    }

    /**
     * 测试thenAccept（对单个结果进行消费）
     * 当前面的CompletableFuture正常完成计算的时候，就会执行提供的action消费这个异步的结果
     * @throws Exception
     */
    public static void test5() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            int result = 100;
            LOGGER.info("第1次运算 start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("第1次运算 end，result：{}", result);
            return result;
        }).thenAccept(num -> {
            LOGGER.info("第2次运算 start，取到第1次运算的结果：{}", num);
            int result = num * 3;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("第2次运算 end，result：{}", result);
        });

        future.get();
    }

    /**
     * 测试thenAcceptBoth（对两个结果进行消费）
     * 当两个CompletableFuture都正常完成计算的时候，就会执行提供的action消费两个异步的结果
     * @throws Exception
     */
    public static void test6() throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int result = 100;
            return result;
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int result = 200;
            return result;
        });

        // future1.thenAcceptBoth是无返回值的
        future1.thenAcceptBoth(future2, new BiConsumer<Integer, Integer>() {
            @Override
            public void accept(Integer x, Integer y) {
                LOGGER.info("最终计算结果：{}", (x + y));
            }
        });

        Integer result = future1.get();
        LOGGER.info("future1.get {}", result);
    }

    /**
     * 测试thenRun
     * thenRun会在上一阶段CompletableFuture计算完成的时候执行一个Runnable，而Runnable并不使用该CompletableFuture的计算结果
     * @throws Exception
     */
    public static void test7() throws Exception {
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            int result = 100;
            LOGGER.info("supplyAsync, result: {}", result);
            return result;
        }).thenRun(() -> {
            LOGGER.info("进入thenRun");
            LOGGER.info("离开thenRun");
        });

        future.get();
    }

    /**
     * 测试thenCombine
     * 合并两个线程任务的结果，并进一步处理。
     * @throws Exception
     */
    public static void test8() throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int result = 100;
            LOGGER.info("任务1结果：{}", result);
            return result;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int result = 200;
            LOGGER.info("任务2结果：{}", result);
            return result;
        });
        CompletableFuture<Integer> result = future1.thenCombine(future2, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer x, Integer y) {
                int num = x + y;
                LOGGER.info("合并后的结果：{}", num);
                return num;
            }
        });
        LOGGER.info("result: {}", result.get());
    }

    /**
     * 测试applyToEither
     * 两个线程任务相比较，先获得执行结果的，就对该结果进行下一步的转化操作。
     * @throws Exception
     */
    public static void test9() throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int num = new Random().nextInt(10);
            LOGGER.info("任务1结果：{}", num);
            try {
                TimeUnit.SECONDS.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return num;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int num = new Random().nextInt(10);
            LOGGER.info("任务2结果：{}", num);
            try {
                TimeUnit.SECONDS.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return num;
        });

        CompletableFuture<Integer> result = future1.applyToEither(future2, new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer num) {
                LOGGER.info("最快结果：{}", num);
                return num * 2;
            }
        });
        LOGGER.info("最终结果为：{}", result.get());
    }

    /**
     * 测试acceptToEither
     * 两个线程任务相比较，先获得执行结果的，就对该结果进行下一步的消费操作。
     * @throws Exception
     */
    public static void test10() throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int num = new Random().nextInt(10);
            LOGGER.info("任务1结果：{}", num);
            try {
                TimeUnit.SECONDS.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return num;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int num = new Random().nextInt(10);
            LOGGER.info("任务2结果：{}", num);
            try {
                TimeUnit.SECONDS.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return num;
        });

        CompletableFuture<Void> result = future1.acceptEither(future2, new Consumer<Integer>() {
            @Override
            public void accept(Integer num) {
                LOGGER.info("最快结果为：{}", num);
            }
        });
        result.join();
    }

    /**
     * 测试runAfterEither
     * 两个线程任务相比较，有任何一个执行完成，就进行下一步操作，不关心运行结果。
     * @throws Exception
     */
    public static void test11() throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int num = new Random().nextInt(10);
            LOGGER.info("任务1结果：{}", num);
            try {
                TimeUnit.SECONDS.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return num;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int num = new Random().nextInt(10);
            LOGGER.info("任务2结果：{}", num);
            try {
                TimeUnit.SECONDS.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return num;
        });

        CompletableFuture<Void> result = future1.runAfterEither(future2, new Runnable() {
            @Override
            public void run() {
                LOGGER.info("已经有一个任务完成了");
            }
        });
        result.join();
    }

    /**
     * 测试anyOf
     * @throws Exception
     */
    public static void test12() throws Exception {
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            int num = new Random().nextInt(10);
            LOGGER.info("任务1结果：{}", num);
            try {
                TimeUnit.SECONDS.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return num;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            int num = new Random().nextInt(10);
            LOGGER.info("任务2结果：{}", num);
            try {
                TimeUnit.SECONDS.sleep(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return num;
        });

        CompletableFuture<Object> result = CompletableFuture.anyOf(future1, future2);
        result.join();
        LOGGER.info("最快结果为：{}", result.get());
    }

    /**
     * 测试allOf
     * @throws Exception
     */
    public static void test13() throws Exception {
        LOGGER.info("进入test13方法");
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("future1完成");
            return 1;
        });
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LOGGER.info("future2完成");
            return 2;
        });

        CompletableFuture<Void> result = CompletableFuture.allOf(future1, future2);
        result.join();
        LOGGER.info("所有任务执行完成");
    }
}
