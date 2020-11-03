package comparator;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 6.2.2 Comparator接口
 * Created by shucheng on 2020/10/10 16:24
 */
public class ComparatorTest {

    public static void main(String[] args) {
        Comparator<String> comp = new LengthComparator();
        String[] friends = {"Peter", "Paul", "Mary", "Liz"};
        Arrays.sort(friends, comp);
        System.out.println(Arrays.toString(friends));
    }
}

class LengthComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        int length1 = o1.length();
        int length2 = o2.length();
        // 下面的代码可以改成 Integer.compare(length1, length2)
        return length1 < length2 ? -1 : ((length1 == length2) ? 0 : 1);
    }
}