package lotteryOdds;

import java.util.Scanner;

/**
 * 3.8.4 确定循环 for
 * This program demonstrates a <code>for</code> loop.
 *
 * Created by shucheng on 2022/10/3 20:40
 */
public class LotteryOdds {

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
        int lotteryOdds = 1;
        for (int i = 1; i <= k; i++)
            lotteryOdds = lotteryOdds * (n - i + 1) / i;


        System.out.println("Your odds are 1 in " + lotteryOdds + ". Good luck!");
    }
}