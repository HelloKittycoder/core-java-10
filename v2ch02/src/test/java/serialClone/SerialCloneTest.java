package serialClone;

import objectStream.serialObject.SerializeUtil;

/**
 * 2.4.6 为克隆使用序列化
 * 通过使用序列化来实现对象的深拷贝（deep copy）
 * Created by shucheng on 2020/9/22 20:56
 */
public class SerialCloneTest {

    public static void main(String[] args) throws CloneNotSupportedException {
        Employee harry = new Employee("Harry Hacker", 35000, 1989, 10, 1);
        // clone harry
        Employee harry2 = (Employee) harry.clone();
        // 其实这里可以把deepCopy写成一个工具方法，不需要写到clone方法里，然后再让某个类来继承
        Employee harry3 = (Employee) SerializeUtil.deepCopy(harry);

        // mutate harry
        harry.raiseSalary(10);

        // now harry and the clone are different
        System.out.println(harry);
        System.out.println(harry2);
        System.out.println(harry3);
    }
}
