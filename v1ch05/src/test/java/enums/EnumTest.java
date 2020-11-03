package enums;

import org.junit.Test;

import java.util.Scanner;

/**
 * 5.6 枚举类
 * This program demonstrates enumerated types.
 *
 * Created by shucheng on 2020/10/9 17:41
 */
public class EnumTest {

    public static void main(String[] args) {
        Scanner in  = new Scanner(System.in);
        System.out.print("Enter a size: (SMALL, MEDIUM, LARGE, EXTRA_LARGE) ");
        String input = in.next().toUpperCase();
        Size size = Enum.valueOf(Size.class, input);
        System.out.println("size=" + size);
        System.out.println("abbreviation=" + size.getAbbreviation());
        if (size == Size.EXTRA_LARGE)
            System.out.println("Goob job--you paid attention to the _.");

        in.close();
    }

    // 测试一些基础方法的使用
    @Test
    public void test() {
        // toString：返回枚举常量名
        System.out.println(Size.SMALL.toString()); // SMALL
        // valueOf：toString的逆方法，通过枚举常量名得到枚举类型
        Size s = Enum.valueOf(Size.class, "SMALL");
        System.out.println(s);
        // values：返回一个包含全部枚举值的数组
        Size[] values = Size.values();
        System.out.println(values);
        // ordinal：返回enum声明中枚举常量的位置，位置从0开始计数
        System.out.println(Size.MEDIUM.ordinal()); // 1
    }

    // 通过反射获取枚举常量（8.9 反射和泛型 P337）
    @Test
    public void test2() {
        // T[] getEnumConstants
        // 如果T是枚举类型，则返回所有值组成的数组
        Size[] enumConstants = Size.class.getEnumConstants();
        System.out.println(enumConstants);

        // 否则返回null
        String[] enumConstants2 = String.class.getEnumConstants();
        System.out.println(enumConstants2);
    }
}

enum Size {
    SMALL("S"), MEDIUM("M"), LARGE("L"), EXTRA_LARGE("XL");

    Size(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    private String abbreviation;
}