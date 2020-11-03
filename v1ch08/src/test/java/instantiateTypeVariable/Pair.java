package instantiateTypeVariable;

import java.util.function.Supplier;

/**
 * Created by shucheng on 2020/10/15 9:35
 */
public class Pair<T> {

    private T first;
    private T second;

    /**
     * 说明：这里不能调用
     * first = new T();
     * second = new T();
     * 来初始化
     */
    public Pair() {
        first = null;
        second = null;
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public void setSecond(T second) {
        this.second = second;
    }

    // 在不能实例化类型变量时，该如何应对？

    /**
     * 方法1：让调用者提供一个构造器表达式
     * 如：Pair<String> p = Pair.makePair(String::new);
     */
    public static <T> Pair<T> makePair(Supplier<T> constr) {
        return new Pair<>(constr.get(), constr.get());
    }

    /**
     * 方法2：比较传统的解决方法是通过反射调用Class.newInstance方法来构造泛型对象
     * 如：Pair<String> p = Pair.makePair(String::new);
     */
    public static <T> Pair<T> makePair(Class<T> cl) {
        try {
            return new Pair<>(cl.newInstance(), cl.newInstance());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}