package mycompiler_test;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject.Kind;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.security.AllPermission;
import java.security.SecureClassLoader;
import java.util.PropertyPermission;

/**
 * 输出字节码到java class文件
 * Created by shucheng on 2020/9/30 15:02
 */
public class ClassFileManager extends ForwardingJavaFileManager {

    public static void main(String[] args) {
        // System.setSecurityManager(new SecurityManager());
        boolean implies = ClassFileManager.class.getProtectionDomain().implies(new PropertyPermission("java.version", "read"));
        System.out.println(implies);
        /*System.setSecurityManager(new SecurityManager());
        System.out.println(System.getProperty("java.version"));
        System.setProperty("java.version", "1.2.3");
        System.out.println(System.getProperty("java.version"));*/
    }

    /**
     * 存储编译后的字节码
     */
    private JavaClassObject javaClassObject;

    protected ClassFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    /**
     * 编译后加载类
     * @param location
     * @return 返回一个匿名的SecureClassLoader，保存在javaClassObject的byte数组中
     */
    @Override
    public ClassLoader getClassLoader(Location location) {
        return new SecureClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                byte[] bytes = javaClassObject.getBytes();
                return super.defineClass(name, bytes, 0, bytes.length);
            }
        };
    }

    /**
     * 给编译器提供JavaFileObject，编译器会将编译结果写进去
     */
    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, Kind kind, FileObject sibling) throws IOException {
        this.javaClassObject = new JavaClassObject(className, kind);
        return this.javaClassObject;
    }
}