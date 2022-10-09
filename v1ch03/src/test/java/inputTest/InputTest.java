package inputTest;

import java.util.Scanner;

/**
 * 3.7.1 读取输入
 * This program demonstrates console input.
 *
 * Created by shucheng on 2022/10/3 15:52
 */
public class InputTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // get first input
        System.out.print("What is your name? ");
        String name = in.nextLine();

        // get second input
        System.out.print("How old are you? ");
        int age = in.nextInt();

        // display output on console
        System.out.println("Hello, " + name + ". Next year, you'll be " + (age + 1));
    }
}