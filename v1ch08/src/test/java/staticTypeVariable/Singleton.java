package staticTypeVariable;

/**
 * 8.6.7 泛型类的静态上下文中类型变量无效（P325）
 * Created by shucheng on 2020/10/15 11:15
 */
public class Singleton<T> {

    // 不能再静态域或方法中引用类型变量
    /*private static T singleInstance; // Error

    public static T getSingleInstance() {
        if (singleInstance == null) { // construct new instance of T
            return singleInstance;
        }
    }*/
}