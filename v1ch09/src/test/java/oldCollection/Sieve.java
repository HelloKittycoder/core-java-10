package oldCollection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;

/**
 * 9.6.5 位集
 * This program runs the Sieve of Eratosthenes benchmark. It computes all primes up to 2,000,000.
 *
 * Created by shucheng on 2020/10/28 23:39
 */
public class Sieve {

    // public static final Logger LOGGER = LoggerFactory.getLogger(Sieve.class);

    /**
     * 下面的程序将计算2-2000000之间的所有素数（一共有148933个素数）
     *
     * 算法思路：将所有的位置为“开”状态，然后，将已知素数的倍数所对应的位都置为“关”状态，
     * 经过这个操作保留下来的位对应的就是素数。
     */
    public static void main(String[] args) {
        int n = 2000000;
        // int n = 10;
        long start = System.currentTimeMillis();
        BitSet b = new BitSet(n + 1);
        int count = 0;
        int i;
        for (i = 2; i <= n; i++) {
            b.set(i);
            // LOGGER.info("将第{}位置设为“开”状态", i);
        }
        i = 2;

        while (i * i <= n) {
            if (b.get(i)) {
                // LOGGER.info("第{}位置处于“开”状态", i);
                count++;
                int k = 2 * i;
                while (k <= n) {
                    b.clear(k);
                    // LOGGER.info("将第{}位置设为“关”状态", k);
                    k += i;
                }
            }
            i++;
        }

        while (i <= n) {
            if (b.get(i)) count++;
            i++;
        }
        long end = System.currentTimeMillis();
        System.out.println(count + " primes"); // 148933
        System.out.println((end - start) + " milliseconds");
    }
}
