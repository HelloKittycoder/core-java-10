package constructGenericArray;

import java.lang.reflect.Array;
import java.util.List;
import java.util.function.IntFunction;

/**
 * 这个类是从“6.4.7 静态内部类”StaticInnerClassTest中拿过来的
 * Created by shucheng on 2020/10/15 10:07
 */
public class ArrayAlg {

    // 不能像下面这样编写代码，编译会报错。也就是说：不能构造泛型数组
    /*public static <T extends Comparable> T[] minmax(T[] a) {
        T[] mm = new T[2];
    }*/

    // 应对上面这种情况的方案：
    /**
     * 如果数组仅仅作为一个类的私有实例域，就可以将这个数组声明为Object[]，并且在获取
     * 元素时进行类型转换（比如：jdk的ArrayList类中elements就是Object[]，而不是E[]）
     */
    // 下面这种写法就是基于上面这个思路，不过在运行ConstructGenericArrayTest#test时会报ClassCastException
    public static <T extends Comparable> T[] minmax(T... a) {
        Object[] mm = new Object[2];
        return (T[]) mm;
    }

    // 改进后的两种方法：

    /**
     * 方法1：让用户提供一个数组构造器表达式
     * 如：String[] ss = ArrayAlg.minmax2(String[]::new, "Tom", "Dick", "Harry");
     */
    public static <T extends Comparable> T[] minmax2(IntFunction<T[]> constr, T... a) {
        T[] mm = constr.apply(2);
        return mm;
    }

    /**
     * 方法2：比较老式的方法是利用反射，调用Array.newInstance
     */
    public static <T extends Comparable> T[] minmax3(T... a) {
        T[] mm = (T[]) Array.newInstance(a.getClass().getComponentType(), 2);
        return mm;
    }

    public static <T extends Comparable> T[] minmax4(List<T> list) {
        if (list != null && list.size() > 0) {
            // 获取list中的类型变量class对应的数组，然后用来将list转换为数组
            // 说明：这里不能直接写list.toArray()，这样得到的是Object[]，这无法转换为Comparable[]
            Class<? extends Comparable> tClass = list.get(0).getClass();
            T[] o = (T[]) Array.newInstance(tClass, 0);
            return list.toArray(o);
        } else {
            return null;
        }
    }
}