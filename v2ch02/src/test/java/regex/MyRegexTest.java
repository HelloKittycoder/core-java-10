package regex;

import common.util.RegexUtil;
import org.junit.Test;

/**
 * 表2-6 正则表达式语法
 * Created by shucheng on 2022/12/17 15:54
 */
public class MyRegexTest {

    // 字符
    @Test
    public void test1() {
        // 匹配字符a
        System.out.println(RegexUtil.matches("a", "a", "b")); // true false

        /**
         * 匹配\（input实际解析的是\，其中另外一个\是转义用的；
         * regex实际解析的是\\，因为在java中要表示真正的\都需要多加一个\来转义，所以要用4个\）
         */
        System.out.println(RegexUtil.matches("\\\\", "\\")); // true

        /**
         * 被\Q和\E包裹起来的部分，被当作要匹配的普通字符序列
         * e.g. 通常情况下，\w+表示至少有一个字母或数字或下划线出现，即 [a-zA-Z0-9]+
         * 这里由于被\Q和\E包裹起来了，所以仅仅只是匹配字符串 \w+
         */
        System.out.println(RegexUtil.matches("\\Q\\w+\\E", "\\w+", "\\w")); // true false
        System.out.println(RegexUtil.matches("\\w+", "\\w+", "Ab12_")); // false true
    }

    // 字符类
    @Test
    public void test2() {
        // 匹配一个字符，该字符可以是a、b、c、d、e其中任意一个
        System.out.println(RegexUtil.matches("[abcde]", "a", "b", "c", "f")); // true true true false
        // 匹配一个字符，该字符是除a、b、c、d、e以外的任意字符
        System.out.println(RegexUtil.matches("[^abcde]", "f", "g", "h", "a")); // true true true false
        // 字符集的交集，下面要匹配的正则表达式等效于[cd]
        System.out.println(RegexUtil.matches("[[abcd]&&[cdef]]", "a", "b", "c", "d", "e", "f")); // false false true true false false
        // \d \D（\d对应的是[0-9]）
        // 匹配单个数字
        System.out.println(RegexUtil.matches("\\d", "1", "2", "3", "a", "+", " ")); // true true true false false false
        // 匹配单个非数字
        System.out.println(RegexUtil.matches("\\D", "1", "2", "3", "a", "+", " ")); // false false false true true true
        // \w \W（\w对应的是[a-zA-Z0-9_]）
        // 匹配单词字符
        System.out.println(RegexUtil.matches("\\w", "a", "A", "3", "_", "+", " ")); // true true true true false false
        // 匹配非单词字符
        System.out.println(RegexUtil.matches("\\W", "a", "A", "3", "_", "+", " ")); // false false false false true true
        // \s \S（\s对应的是[ \n\r\t\f\x{8}]） TODO: 这个\x{8}不太清楚是什么；另外下面两个打印会有些字消失了，原因未知
        // 匹配空白字符
        System.out.println(RegexUtil.matches("\\s", " ", "\r", "\n", "\t", "\f", "a", "\\")); // 倒数两个是false，剩下的是true
        // 匹配非空白字符
        System.out.println(RegexUtil.matches("\\S", " ", "\r", "\n", "\t", "\f", "a", "\\")); // 倒数两个是true，剩下的是false
    }

    // 序列和选择
    @Test
    public void test3() {
        // XY（任何X中的字符串，后面跟随任何Y中的字符串）
        System.out.println(RegexUtil.matches("[1-9][0-9]*", "0", "1", "2", "10", "11", "21")); // 第一个是false，剩下的是true
        // X|Y（任何X或Y中的字符串）
        System.out.println(RegexUtil.matches("1|2", "1", "2", "3")); // 前两个是true，最后一个是false
    }

    // 群组 TODO:
    @Test
    public void test4() {}

    /**
     * 量词
     * https://www.cnblogs.com/yw0219/p/8047938.html
     * https://www.cnblogs.com/liuxuhui/p/12157146.html
     */
    @Test
    public void test5() {
        // X? 可选X（等效于X{0,1}）
        System.out.println(RegexUtil.matches("\\+?", "", "+", "++")); // true true false
        // X*（0个或多个X，等效于X{0,}），X+（1或多个X，等效于X{1,}）
        System.out.println(RegexUtil.matches("\\+*", "", "+", "++")); // true true true
        System.out.println(RegexUtil.matches("\\++", "", "+", "++")); // false true true
        // X{n}（n个X），X{n,}（至少n个X），X{m,n}（m到n个X，两端都包含）
        System.out.println(RegexUtil.matches("\\+{2}", "", "+", "++", "+++", "++++")); // false false true false false
        System.out.println(RegexUtil.matches("\\+{2,}", "", "+", "++", "+++", "++++")); // false false true true true
        System.out.println(RegexUtil.matches("\\+{1,3}", "", "+", "++", "+++", "++++")); // false true true true false
        // Q?，非贪婪匹配，其中Q是一个量词表达式 TODO:
        // Q+，贪婪匹配（默认就是贪婪匹配），其中Q是一个量词表达式 TODO:
    }

    // 边界匹配
    @Test
    public void test6() {
        // ^，$ 输入的开头和结尾（或者多行模式中的开头和结尾行） TODO: 多行模式后续有空再看看
        System.out.println(RegexUtil.matches("^Java$", "Java", "abc")); // true false
        // \b（单词边界），\B（非单词边界）
        System.out.println(RegexUtil.matches("\\bJava\\b", "Java", "abc")); // true false
    }
}
