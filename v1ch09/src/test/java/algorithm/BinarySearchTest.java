package algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 9.5.2 二分查找（P391）
 * Created by shucheng on 10/28/2020 8:11 PM
 */
public class BinarySearchTest {

    public static void main(String[] args) {

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        // Collections.shuffle(list);

        // 注意：集合必须是排好序的，否则算法将返回错误的答案
        int i = Collections.binarySearch(list, 500);
        System.out.println(i);
    }
}
