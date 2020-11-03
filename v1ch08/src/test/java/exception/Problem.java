package exception;

/**
 * 8.6.8 不能抛出或捕获泛型类的实例（P325）
 * Created by shucheng on 2020/10/15 11:20
 */

/**
 * 泛型类扩展Throwable是不合法的，不能正常编译，如下写法会报错
 * public class Problem<T> extends Throwable {
 * }
 */
public class Problem {

    // catch子句中不能使用类型变量
    /*public static <T extends Throwable> void doWork(Class<T> t) {
        try {
            // do work
        } catch (T e) { // Error--can't catch type variable
        }
    }*/

    // 不过以下写法是可以的
    public static <T extends Throwable> void doWork(T t) throws T { // OK
        try {
            // do work
        } catch (Throwable realCause) {
            t.initCause(realCause);
            throw t;
        }
    }
}