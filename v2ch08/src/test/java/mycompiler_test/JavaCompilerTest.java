package mycompiler_test;

import org.junit.Test;

import javax.tools.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

/**
 * Created by shucheng on 2020/9/29 21:47
 * 参考链接：https://blog.csdn.net/u010398771/article/details/90474813
 */
public class JavaCompilerTest {

    // 使用JavaCompiler编译java代码
    @Test
    public void test() throws IOException {
        // 编译java代码
        URL resource = getClass().getResource("/MyTest.java");
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int result = compiler.run(null, null, null, resource.getPath());
        System.out.println(result == 0 ? "编译成功" : "编译失败");

        URL resource2 = getClass().getResource("/"); // target/test-classes目录
        // 执行java命令，并显示执行结果
        Process process = Runtime.getRuntime().exec("java MyTest", null, new File(resource2.getPath()));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            System.out.println(str);
        }
        bufferedReader.close();
    }

    /**
     * StandardJavaFileManager、CompilationTask的使用
     * 参考链接：http://www.javased.com/index.php?source_dir=Android-SQL-Helper/tests/AndroidSQLHelperTest/src/com/sgxmobileapps/androidsqlhelper/test/CompilerTestCase.java
     */
    @Test
    public void test2() {
        // 注意：下面不能直接写File.separator，如果这样写，会提示java.lang.IllegalArgumentException: character to be escaped is missing
        // 这里是基于System.get("user.dir")这个基础路径来找文件的
        String fullQualifiedFileName = "target.test-classes.com.kittycoder.dynamic.".replaceAll("\\.", Matcher.quoteReplacement(File.separator))
                + "Calculator.java";
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(
                null, null, null);

        Iterable<? extends JavaFileObject> files =
                fileManager.getJavaFileObjectsFromStrings(Arrays.asList(fullQualifiedFileName));
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, files);
        Boolean result = task.call();
        if (result) {
            System.out.println("Succeeded");
        }
    }

    /**
     * JavaFileObject获取java源程序
     * 开发者希望生成Calculator的一个测试类，而不是手工编写，使用compiler API，可以将内存中的一段字符串，编译成一个class文件
     */
    @Test
    public void test3() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
        JavaFileObject testFile = generateTest(null);
        List<JavaFileObject> classes = Arrays.asList(testFile);
        JavaCompiler.CompilationTask task = compiler.getTask(null, standardFileManager, null, null, null, classes);
        if (task.call()) {
            System.out.println("success!");
        } else {
            System.out.println("failure!");
        }
    }

    // 获取编译器的诊断信息
    @Test
    public void test4() {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> collector = new DiagnosticCollector<>();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
        JavaFileObject testFile = generateTest(
                "package com.kittycoder.dynamic;\r\n" +
                "class CalculatorTest {\r\n" +
                "   public void testMultiply() {\r\n" +
                "       Calculator c = new Calculator();\r\n" +
                "       System.out.println(c.multiply(2, 4));\r\n" +
                "   }\r\n" +
                "   public static void main(String[] args) {\r\n" +
                "       CalculatorTest ct = new CalculatorTest();\r\n" +
                "       ct.testMultiply()\r\n" +
                "   }\r\n" +
                "}");
        List<JavaFileObject> classes = Arrays.asList(testFile);
        JavaCompiler.CompilationTask task = compiler.getTask(null, standardFileManager, collector, null, null, classes);
        if (task.call()) {
            System.out.println("success!");
        } else {
            System.out.println("failure!");
        }

        List<Diagnostic<? extends JavaFileObject>> diagnostics = collector.getDiagnostics();
        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics) {
            System.out.println("line: " + diagnostic.getLineNumber());
            System.out.println("msg: " + diagnostic.getMessage(Locale.ENGLISH));
            System.out.println("source: " + diagnostic.getSource());
        }
    }

    private static JavaFileObject generateTest(String contents) {
        if (contents == null || "".equals(contents)) {
            contents = "package com.kittycoder.dynamic;\r\n" +
                    "class CalculatorTest {\r\n" +
                    "   public void testMultiply() {\r\n" +
                    "       Calculator c = new Calculator();\r\n" +
                    "       System.out.println(c.multiply(2, 4));\r\n" +
                    "   }\r\n" +
                    "   public static void main(String[] args) {\r\n" +
                    "       CalculatorTest ct = new CalculatorTest();\r\n" +
                    "       ct.testMultiply();\r\n" +
                    "   }\r\n" +
                    "}";
        }
        StringObject so = new StringObject("com.kittycoder.dynamic.CalculatorTest", contents);;
        return so;
    }

    // 测试自定义编译器
    @Test
    public void test5() throws Exception {
        StringBuilder src = new StringBuilder();
        src.append("package com.kittcoder.dynamic;\r\n");
        src.append("public class DynaClass {\r\n");
        src.append("    public String toString() {\r\n");
        src.append("        return \"Hello, I am \" + ");
        src.append("            this.getClass().getSimpleName();\r\n");
        src.append("    }\r\n");
        src.append("}\r\n");

        String fullName = "com.kittcoder.dynamic.DynaClass";
        DynamicCompiler compiler = new DynamicCompiler();
        Class clz = compiler.compileAndLoad(fullName, src.toString());

        System.out.println(clz.getConstructor().newInstance());
        compiler.closeFileManager();
    }
}
