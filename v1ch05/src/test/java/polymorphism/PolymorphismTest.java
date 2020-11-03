package polymorphism;

import org.junit.Test;

/**
 * 5.1.5 多态
 * Created by shucheng on 2020/10/9 11:15
 */
public class PolymorphismTest {

    // 将子类的对象赋给超类变量
    @Test
    public void test() {
        // 一个Employee变量既可以引用一个Employee类对象
        Employee e = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        System.out.println(e);

        // 也可以引用一个Employee类的任何一个子类的对象（例如：Manager）
        e = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        System.out.println(e);
    }

    // 正常使用
    @Test
    public void test2() {
        Manager[] managers = new Manager[10];
        Employee[] staff = managers;
        // 上面这两行等同于 Employee[] staff = new Manager[10];

        staff[0] = new Manager("Carl Cracker", 80000, 1987, 12, 15);
    }

    // 错误使用
    @Test(expected = ArrayStoreException.class)
    public void test3() {
        Manager[] managers = new Manager[10];
        Employee[] staff = managers;
        /**
         * 下面这行会出错，因为所有声明的数组都会牢记创建它们的元素类型，并负责监督仅将
         * 类型兼容的引用存储到数组中
         */
        staff[0] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
    }
}