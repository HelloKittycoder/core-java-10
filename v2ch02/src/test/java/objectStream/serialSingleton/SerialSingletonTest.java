package objectStream.serialSingleton;

import common.FileUtil;
import org.junit.Test;

import java.io.*;

/**
 * 2.4.4 序列化单例和类型安全的枚举
 * Created by shucheng on 2020/9/22 9:28
 */
public class SerialSingletonTest {

    @Test
    public void test() throws IOException, ClassNotFoundException {
        // 单例 需要实现Serializable接口，还需要重写readResolve方法
        Orientation original = Orientation.HORIZONTAL;
        String path = FileUtil.getResourcePath("objectStream/serialSingleton/orientation.dat");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(original);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))) {
            Orientation saved = (Orientation) in.readObject();
            System.out.println(saved == original);
        }

        /**
         * 枚举 不需要实现Serializable接口，也不需要重写readResolve方法
         * 为什么enum不需要继承Serializable接口就能直接序列化呢？
         * 因为enum隐式继承了Enum类，而Enum类有实现了Serializable接口
         */
        /*Type genericInterfaces = OrientationEnum.class.getGenericSuperclass();
        System.out.println(genericInterfaces);*/
        OrientationEnum original2 = OrientationEnum.HORIZONTAL;
        String path2 = FileUtil.getResourcePath("objectStream/serialSingleton/orientationEnum.dat");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path2))) {
            out.writeObject(original2);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(path2))) {
            OrientationEnum saved2 = (OrientationEnum) in.readObject();
            System.out.println(saved2 == original2);
        }
    }
}