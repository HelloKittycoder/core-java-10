package classLoader2;

/**
 * 把文件放在resources目录下，主要是不让Calculator编译成class文件，然后在运行时加载到虚拟机里
 * 这里主要是想把Calculator这个类进行加密处理
 *
 * 方法：
 * 1.可以手动运行javac命令编译Calculator，生成class文件，然后再写个加密方法，手动运行下
 * 获得加密过的class文件
 *
 * 2.可以利用第8章的JavaCompiler类的相关api来直接编译Calculator生成class文件，然后再写个加密方法，
 * 手动运行下，获得加密过的class文件
 *
 * 这里采用第2种方式，详细代码见ClassLoaderTest2
 *
 * Created by shucheng on 2020/10/5 19:13
 */
public class Calculator {

    public static void main(String[] args) {
        System.out.println("测试Calculator的main方法");
    }

    public Calculator() {
        System.out.println("Creating a Calculator instance.");
    }

    public int add(int a, int b) {
        return a + b;
    }

    public int minus(int a, int b) {
        return a - b;
    }
}
