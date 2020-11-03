package arrays;

import org.junit.Test;

import java.util.Arrays;

/**
 * 测试System.arraycopy的使用
 * Created by shucheng on 2020/10/10 13:18
 */
public class ArrayCopyTest {

    @Test
    public void test() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] newarr = new int[4];
        System.arraycopy(arr, 0, newarr, 0, 4);
        System.out.println(Arrays.toString(newarr)); // [1, 2, 3, 4]
    }

    @Test
    public void test2() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] newarr = new int[5];
        System.arraycopy(arr, 0, newarr, 2, 3);
        System.out.println(Arrays.toString(newarr)); // [0, 0, 1, 2, 3]
    }

    // 测试for循环复制和System.arraycopy复制的时间差别
    @Test
    public void test3() {
        int[] arr = new int[1000000];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        long b1 = System.currentTimeMillis();
        int[] newarr = new int[1000000];
        for (int i = 0; i < arr.length; i++) {
            newarr[i] = arr[i];
        }
        long e1 = System.currentTimeMillis();
        System.out.println(e1 - b1); // 5ms

        long b2 = System.currentTimeMillis();
        int[] newarr2 = new int[1000000];
        System.arraycopy(arr, 0, newarr2, 0, arr.length);
        long e2 = System.currentTimeMillis();
        System.out.println(e2 - b2); // 2ms
    }
}