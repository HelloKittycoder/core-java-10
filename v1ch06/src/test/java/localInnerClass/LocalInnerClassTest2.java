package localInnerClass;

import java.util.Arrays;
import java.util.Date;

/**
 * 6.4.5 由外部方法访问变量
 * 统计一下在排序过程中调用compareTo方法的次数
 *
 * Created by shucheng on 2020/10/13 10:49
 */
public class LocalInnerClassTest2 {

    /**
     * 说明：下面写int counter肯定不行，因为局部内部类需要引用final变量
     * 这里改用int[]，引用不变，但内部的数据发生变化
     * @param args
     */
    public static void main(String[] args) {
        int[] counter = {0};
        Date[] dates = new Date[100];
        for (int i = 0; i < dates.length; i++) {
            dates[i] = new Date() {
                @Override
                public int compareTo(Date anotherDate) {
                    counter[0]++; // Error
                    return super.compareTo(anotherDate);
                }
            };
        }
        Arrays.sort(dates);
        System.out.println(counter[0]);
    }
}