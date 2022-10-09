package bigIntegerTest;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * 3.9 大数值
 * This program uses big numbers to compute the odds of winning the grand prize in a lottery.
 *
 * Created by shucheng on 2022/10/3 20:53
 */
public class BigIntegerTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        /**
         * 从1~50中取6个数字来抽奖
         * k = 6
         * n = 50
         * 中奖几率为：1/15890700
         */
        System.out.print("How many numbers do you need to draw? ");
        int k = in.nextInt();

        System.out.print("What is the highest number you can draw? ");
        int n = in.nextInt();

        /**
         * compute binomial coefficient n*(n-1)*(n-2)*...*(n-k+1)/(1*2*3*...*k)
         */
        BigInteger lotteryOdds = BigInteger.ONE;
        for (int i = 1; i <= k; i++)
            lotteryOdds = lotteryOdds.multiply(BigInteger.valueOf(n - i + 1))
                    .divide(BigInteger.valueOf(i));

        System.out.println("Your odds are 1 in " + lotteryOdds + ". Good luck!");
    }
}