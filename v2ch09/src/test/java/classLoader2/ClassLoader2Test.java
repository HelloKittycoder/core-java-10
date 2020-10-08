package classLoader2;

import org.junit.Test;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;

/**
 * 结合书籍中关于加密class文件的基本思路，去掉了JFrame相关东西，重新写了下，见test4
 * test、test2、test3只是复习下JavaCompiler的使用
 * Created by shucheng on 2020/10/5 19:11
 */
public class ClassLoader2Test {


    // 复习下使用JavaCompiler编译class文件，有实际的class文件生成
    @Test
    public void test() throws URISyntaxException {
        // 到target/classes目录下运行 java classLoader2.Calculator 就能看到效果了
        URL resource = ClassLoader.getSystemResource("classLoader2/Calculator.java");
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        String[] args = {"-encoding", "UTF-8", resource.getPath()};
        /**
         * 如何添加编译参数
         * 参考链接：https://stackoverflow.com/questions/14848202/javax-tools-javacompiler-arguments-arg
         */
        int result = compiler.run(null, null, null, args);
        System.out.println(result == 0 ? "编译成功" : "编译失败");
        // 清理编译的class文件
        URL resource2 = ClassLoader.getSystemResource("classLoader2/Calculator.class");
        System.out.println(resource2);
        Paths.get(resource2.toURI()).toFile().delete();
    }

    /**
     * 使用JavaCompiler编译class文件，没有实际的class文件生成，编译出的内容在内存中
     * 输入输出都在内存中（输入的代码来源是String，输出的class内容放在byte[]中）
     * 参考链接：https://www.cnblogs.com/yanjie-java/p/7940929.html
     */
    @Test
    public void test2() throws IOException {
        StringBuilder src = new StringBuilder();
        src.append("package com.kittcoder.dynamic;\r\n");
        src.append("public class MyTest {\r\n");
        src.append("    public static void main(String[] args) {\r\n");
        src.append("        System.out.println(\"This is MyTest\");");
        src.append("    }\r\n");
        src.append("}\r\n");

        Map<String, byte[]> results;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
            JavaFileObject javaFileObject = manager.makeStringSource("MyTest", src.toString());
            /**
             * 下面这行原本写的是：compiler.getTask(null, manager, null, null, null, Arrays.asList(javaFileObject))，
             * 因为windows下执行mvn test会报编码gbk不可映射字符，就加了encoding这个选项
             */
            CompilationTask task = compiler.getTask(null, manager, null, Arrays.asList(new String[]{"-encoding", "UTF-8"}), null,
                    Arrays.asList(javaFileObject));
            if (task.call()) {
                // System.out.println("task调用成功");
                results = manager.getClassBytes();
                MemoryClassLoader loader = new MemoryClassLoader(results);
                Class<?> clazz = loader.loadClass("com.kittcoder.dynamic.MyTest");
                // System.out.println(clazz);
                /**
                 * 下面为什么要写m.invoke(null, (Object) new String[0])，而不是写m.invoke(null, new String[0]);
                 * 如果写后者的话，会报java.lang.IllegalArgumentException: wrong number of arguments
                 * 关于这个问题，打断点看下method.invoke(Object obj, Object... args)中的args参数很容易就明白
                 * 解释：使用前者的话，main方法刚好接到一个参数new String[0]，
                 * 使用后者的话，就接不到一个参数，所以会报参数个数不匹配
                 *
                 * 另外说下：写new String[0]和new String[]{}的效果一样，都是数组，且length为0
                 */
                Method m = clazz.getMethod("main", String[].class);
                m.invoke(null, (Object) new String[0]);
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输入来自文件，输出是在内存（输入的代码来源是java文件，输出的class内容放在byte[]中）
     * 参考链接：https://www.cnblogs.com/yanjie-java/p/7940929.html
     */
    @Test
    public void test3() throws URISyntaxException, IOException {
        Map<String, byte[]> results;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
            URL resource = ClassLoader.getSystemResource("classLoader2/Calculator.java");
            Iterable<? extends JavaFileObject> files = stdManager.getJavaFileObjects(new File(resource.toURI()));
            CompilationTask task = compiler.getTask(null, manager, null, Arrays.asList(new String[]{"-encoding", "UTF-8"}), null,
                    files);
            if (task.call()) {
                // System.out.println("task调用成功");
                results = manager.getClassBytes();
                MemoryClassLoader loader = new MemoryClassLoader(results);
                Class<?> clazz = loader.loadClass("classLoader2.Calculator");
                // System.out.println(clazz);
                Method m = clazz.getMethod("main", String[].class);
                m.invoke(null, (Object) new String[0]);
                // 创建一个Calculator实例
                Constructor<?> ctor = clazz.getConstructor(null);
                Object o = ctor.newInstance(null);
                // 调用add方法
                Method addMethod = clazz.getDeclaredMethod("add", int.class, int.class);
                System.out.println(addMethod.invoke(o, 1, 2)); // 3
                // 调用minus方法
                Method minusMethod = clazz.getDeclaredMethod("minus", int.class, int.class);
                System.out.println(minusMethod.invoke(o, 1, 2)); // -1
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() throws Exception {
        int key = 3; // 加密用的key
        // 生成加密过的class文件
        generateCryptClassFile(key);
        // 读取加密过的class文件，并加载到虚拟机中
        CryptClassLoader loader = new CryptClassLoader(key);
        Class<?> clazz = loader.loadClass("classLoader2.Calculator");
        // System.out.println(clazz);
        Method m = clazz.getMethod("main", String[].class);
        m.invoke(null, (Object) new String[0]);
        // 创建一个Calculator实例
        Constructor<?> ctor = clazz.getConstructor(null);
        Object o = ctor.newInstance(null);
        // 调用add方法
        Method addMethod = clazz.getDeclaredMethod("add", int.class, int.class);
        System.out.println(addMethod.invoke(o, 1, 2)); // 3
        // 调用minus方法
        Method minusMethod = clazz.getDeclaredMethod("minus", int.class, int.class);
        System.out.println(minusMethod.invoke(o, 1, 2)); // -1
    }

    private static void generateCryptClassFile(int key) throws URISyntaxException, IOException {
        Map<String, byte[]> results;
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager stdManager = compiler.getStandardFileManager(null, null, null);
        try (MemoryJavaFileManager manager = new MemoryJavaFileManager(stdManager)) {
            URL resource = ClassLoader.getSystemResource("classLoader2/Calculator.java");
            Iterable<? extends JavaFileObject> files = stdManager.getJavaFileObjects(new File(resource.toURI()));
            CompilationTask task = compiler.getTask(null, manager, null, Arrays.asList(new String[]{"-encoding", "UTF-8"}), null,
                    files);
            if (task.call()) {
                // System.out.println("task调用成功");
                results = manager.getClassBytes();
                byte[] classBytes = results.get("classLoader2.Calculator");

                try (FileOutputStream out = new FileOutputStream(ClassLoader.getSystemResource("").getPath() + "classLoader2/Calculator.secret")) {
                    for (byte ch : classBytes) {
                        out.write(ch + key);
                    }
                }
            }
        }
    }
}
