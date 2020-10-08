package compiler;

import javax.swing.*;
import javax.tools.*;
import javax.tools.JavaFileObject.Kind;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * 8.2.3 一个示例：动态Java代码生成
 *
 * 里面用到了 buttons2.ButtonFrame
 * Created by shucheng on 2020/9/29 18:01
 */
public class CompilerTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        final List<ByteArrayJavaClass> classFileObjects = new ArrayList<>();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        JavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        fileManager = new ForwardingJavaFileManager<JavaFileManager>(fileManager) {
            @Override
            public JavaFileObject getJavaFileForOutput(Location location, String className,
                   Kind kind, FileObject sibling) throws IOException {
                if (className.startsWith("x.")) {
                    ByteArrayJavaClass fileObject = new ByteArrayJavaClass(className);
                    classFileObjects.add(fileObject);
                    return fileObject;
                } else {
                    return super.getJavaFileForOutput(location, className, kind, sibling);
                }
            }
        };

        String frameClassName = args.length == 0 ? "buttons2.ButtonFrame" : args[0];
        JavaFileObject source = buildSource(frameClassName);
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null,
                null, Arrays.asList(source));
        Boolean result = task.call();

        for (Diagnostic<? extends JavaFileObject> d : diagnostics.getDiagnostics()) {
            System.out.println(d.getKind() + ":" + d.getMessage(null));
        }
        fileManager.close();
        if (!result) {
            System.out.println("Compilation failed.");
            System.exit(1);
        }

        EventQueue.invokeLater(() -> {
            try {
                Map<String, byte[]> byteCodeMap = new HashMap<>();
                for (ByteArrayJavaClass cl : classFileObjects)
                    byteCodeMap.put(cl.getName().substring(1), cl.getBytes());

                ClassLoader loader = new MapClassLoader(byteCodeMap);
                JFrame frame = (JFrame) loader.loadClass("x.Frame").newInstance();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("CompilerTest");
                frame.setVisible(true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Builds the source for the subclass that implements the addEventHandlers method.
     * @return a file object containing the source in a string builder
     */
    static JavaFileObject buildSource(String superclassName) throws IOException, ClassNotFoundException {
        StringBuilderJavaSource source = new StringBuilderJavaSource("x.Frame");
        source.append("package x;\r\n");
        source.append("public class Frame extends " + superclassName + "{");
        source.append("protected void addEventHandlers() {");
        final Properties props = new Properties();
        /**
         * 说明：superclassName对应buttons2.ButtonFrame，类路径是/buttons2
         * 下面getResource("action.properties")是相对路径，找到的是/buttons2/action.properties
         * 如果写绝对路径的话就是，getResource("/buttons2/action.properties")
         */
        props.load(Class.forName(superclassName).getResourceAsStream("action.properties"));
        for (Map.Entry<Object, Object> e : props.entrySet()) {
            String beanName = (String) e.getKey();
            String eventCode = (String) e.getValue();
            source.append(beanName + ".addActionListener(event -> {");
            source.append(eventCode);
            source.append("});");
        }
        source.append("}}");
        return source;
    }
}