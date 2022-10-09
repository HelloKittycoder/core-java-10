package retirement;

import java.util.Scanner;

/**
 * 3.8.3 循环 while
 * This program demonstrates a <code>while</code> loop.
 *
 * Created by shucheng on 2022/10/3 19:29
 */
public class Retirement {

    public static void main(String[] args) {
        /**
         * retire money: 100 0000
         * money every year: 15 0000
         * interest rate: 2%
         * -->7 years
         *
         * calculate process:
         * balance:150000.0 interest:3000.0
         * balance:303000.0 interest:6060.0
         * balance:459060.0 interest:9181.2
         * balance:618241.2 interest:12364.823999999999
         * balance:780606.024 interest:15612.12048
         * balance:946218.14448 interest:18924.3628896
         * balance:1115142.5073696 interest:22302.850147392
         */

        // read inputs
        Scanner in = new Scanner(System.in);

        System.out.print("How much money do you need to retire? ");
        double goal = in.nextDouble();

        System.out.print("How much money will you contribute every year? ");
        double payment = in.nextDouble();

        System.out.print("Interest rate in %: ");
        double interestRate = in.nextDouble();

        double balance = 0;
        int years = 0;

        // update account balance while goal isn't reached
        while (balance < goal) {
            // add this year's payment and interest
            balance += payment;
            double interest = balance * interestRate / 100;
            // System.out.println("balance:" + balance + " interest:" + interest);
            balance += interest;
            years++;
        }

        System.out.print("You can retire in " + years + " years.");
    }
}