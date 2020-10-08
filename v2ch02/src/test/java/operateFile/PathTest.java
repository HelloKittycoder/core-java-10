package operateFile;

import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 2.5.1 Path
 * Created by shucheng on 2020/9/22 22:35
 */
public class PathTest {

    @Test
    public void test() {
        Path absolute = Paths.get("/home", "harry"); // 绝对路径
        System.out.println(absolute);
        System.out.println(Paths.get("C:", "Windows"));
        Path relative = Paths.get("myprog", "conf", "user.properties"); // 相对路径
        System.out.println(relative);

        /**
         * 组合或解析路径
         * 对p.resolve(q)作下说明：
         * 如果q是绝对路径，直接返回q
         * 否则，根据文件系统的规则，将“p后面跟着q”作为结果
         */
        Path baseDir = Paths.get("D:", "home"); // 绝对路径 D:\home
        Path workRelative = Paths.get("work"); // 相对路径 work
        // 下面也可以直接写成
        // Path workPath = baseDir.resolve("work");
        Path workPath = baseDir.resolve(workRelative); // 绝对路径 D:\home\work
        System.out.println(workPath);

        // resolveSibling：通过解析指定路径的父路径产生其兄弟路径
        Path appWorkPath = Paths.get("/opt/myapp/work");
        Path tempPath = appWorkPath.resolveSibling("temp"); // \opt\myapp\temp
        System.out.println(tempPath);

        // p.relativize(q)=r 返回的是q在以p为基准目录时的相对路径
        // 可以理解为：当前处在目录p，沿着返回的相对路径r，就可以通向q
        Path p = Paths.get("/home/cay");
        Path q = Paths.get("/home/fred/myprog");
        System.out.println(p.relativize(q)); // ..\fred\myprog

        // p.normalize() 将会移除p中所有冗余的..和.
        Path redundantPath = Paths.get("/home/cay/../fred/./myprog");
        Path normalizedPath = redundantPath.normalize(); // \home\fred\myprog
        System.out.println(normalizedPath);

        /**
         * p.toAbsolutePath() 返回给定路径p对应的绝对路径
         * p是绝对路径，则直接返回
         * 否则，返回“当前代码的运行路径跟着p”作为结果
         */
        Path testPath = Paths.get("worksoft");
        Path absolutePath = testPath.toAbsolutePath();
        System.out.println(absolutePath);

        // 其他常用方法：getParent，getFileName，getRoot
        Path testPath2 = Paths.get("/home", "fred", "myprog.properties");
        System.out.println(testPath2.getParent()); // \home\fred
        // 对getFileName作下说明：这个FileName别只理解为文件名，
        // 当Path为文件时，返回文件名；当Path为目录时，返回目录名字
        System.out.println(testPath2.getFileName()); // myprog.properties
        System.out.println(testPath2.getRoot()); // \

        // File有个toPath方法，Path有个toFile方法（jdk为了与旧版常用的File类做兼容而做的设计）
        File file = new File("D:/worksoft");
        System.out.println(file.toPath());
        Path worksoftPath = Paths.get("D:", "worksoft");
        System.out.println(worksoftPath.toFile());
    }
}
