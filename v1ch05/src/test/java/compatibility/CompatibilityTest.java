package compatibility;

import java.util.ArrayList;

/**
 * 5.3.2 类型化与原始数组列表的兼容性（P183）
 *
 * 执行命令需要进到test/java目录下
 * 使用命令javac -encoding UTF-8 compatibility/CompatibilityTest.java进行编译时，会报
 * compatibility\CompatibilityTest.java使用了未经检查或不安全的操作，但没明确说是哪个位置引起的
 * （这个是由“ct.find”那一行引起的）
 *
 * 要想明确看到这个警告是哪个地方报的，将命令改成
 * javac -encoding UTF-8 -Xlint:unchecked compatibility/CompatibilityTest.java
 *
 * 就算是加类型转换也还是会出现警告。如果想避免出现警告，直接在下面加上@SuppressWarnings("unchecked")
 *
 * Created by shucheng on 2020/10/9 16:35
 */
public class CompatibilityTest {

    public static void main(String[] args) {
        CompatibilityTest ct = new CompatibilityTest();
        ArrayList<String> list = new ArrayList<>();
        ct.update(list);

        // @SuppressWarnings("unchecked")
        ArrayList<String> result = ct.find("testsql");
        System.out.println(result);
    }

    public void update(ArrayList list) {
        System.out.println("调用CompatibilityTest#update方法");
    }

    public ArrayList find(String query) {
        System.out.println("调用CompatibilityTest#find方法");
        return new ArrayList();
    }
}