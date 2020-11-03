package algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 9.5.3 简单算法
 * Created by shucheng on 2020/10/28 22:09
 */
public class SimpleAlgorithmTest {

    @Test
    public void test() {
        List<String> words = new ArrayList<>();
        words.add("A++");
        words.add("B++");
        words.add("C++");
        words.add("D++");
        words.add("C++");
        System.out.println(words);
        // 将words中所有为“C++”的项替换为“Java”
        boolean isReplace = Collections.replaceAll(words, "C++", "Java");
        System.out.println(isReplace);
        System.out.println(words);
    }

    @Test
    public void test2() {
        List<String> words = new ArrayList<>();
        words.add("Aaa");
        words.add("BBB");
        words.add("CcC");
        System.out.println(words);
        // 将words中的所有项改成小写形式
        words.replaceAll(String::toLowerCase);
        System.out.println(words);
    }

    @Test
    public void test3() {
        List<String> words = new ArrayList<>();
        words.add("apple");
        words.add("banana");
        words.add("cherry");
        words.add("car");
        words.add("ox");
        System.out.println(words);
        // 将words中所有字母数量小于等于3的单词删掉
        words.removeIf(w -> w.length() <= 3);
        System.out.println(words);
    }

    @Test
    public void test4() {
        List<String> originalList = new ArrayList<>();
        originalList.add("aa");
        originalList.add("bb");
        originalList.add("cc");
        originalList.add("dd");

        // 初始化目标list的size，注意不是capacity（容量） new ArrayList<>(4)里面的4是容量
        List<String> arrayList = new ArrayList<>(Arrays.asList(new String[originalList.size()]));
        System.out.println(arrayList);
        // System.out.println(arrayList.size());

        Collections.copy(arrayList, originalList);
        System.out.println(arrayList);
    }

    @Test
    public void test5() {
        List<String> arrayList = new ArrayList<>(Arrays.asList(new String[10]));
        // 将list中所有位置设置为相同的值
        Collections.fill(arrayList, "aaa");
        System.out.println(arrayList);
    }
}
