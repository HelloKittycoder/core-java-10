package lotteryArray;

/**
 * 3.10.7 不规则数组
 * This program demonstrate a triangular array.
 *
 * Created by shucheng on 2022/10/4 16:24
 */
public class LotteryArray {

    public static void main(String[] args) {
        final int NMAX = 10;

        // allocate triangular array
        int[][] odds = new int[NMAX + 1][];
        for (int n = 0; n < odds.length; n++) {
            odds[n] = new int[n + 1];
        }

        // fill triangular array
        for (int n = 0; n < odds.length; n++) {
            for (int k = 0; k < odds[n].length; k++) {
                // compute binomial coefficient n*(n-1)*(n-2)*...*(n-k+1)/(1*2*3*...*k)
                int lotteryOdds = 1;
                for (int i = 1; i <= k; i++)
                    lotteryOdds = lotteryOdds * (n - i + 1) / i;

                odds[n][k] = lotteryOdds;
            }
        }

        /**
         * lsc注：上面的循环也可以写成如下形式，可能更简单些
         */
        /*for (int n = 0; n < odds.length; n++) {
            int lotteryOdds = 1;
            for (int k = 0; k < odds[n].length; k++) {
                if (k >= 1) {
                    lotteryOdds = lotteryOdds * (n - k + 1) / k;
                }
                odds[n][k] = lotteryOdds;
            }
        }*/

        // print triangular array
        for (int[] row : odds) {
            for (int odd : row)
                System.out.printf("%4d", odd);
            System.out.println();
        }
    }
}