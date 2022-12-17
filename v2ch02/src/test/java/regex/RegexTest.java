package regex;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 打印出群组边界
 * 这个没看懂，后续有空再看
 * Created by shucheng on 2022/12/17 18:11
 */
public class RegexTest {

    public static void main(String[] args) throws PatternSyntaxException {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter pattern: "); // (([1-9]|1[0-2]):([0-5][0-9]))[ap]m
        String patterString = in.nextLine();

        Pattern pattern = Pattern.compile(patterString);

        while (true) {
            System.out.println("Enter string to match: "); // 11:59am
            String input = in.nextLine();
            if (input == null || input.equals("")) return;
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches()) {
                System.out.println("Match");
                int g = matcher.groupCount();
                if (g > 0) {
                    for (int i = 0; i < input.length(); i++) {
                        // Print any empty groups
                        for (int j = 1; j <= g; j++)
                            if (i == matcher.start(j) && i == matcher.end(j))
                                System.out.print("()");
                        // Print ( for non-empty groups starting here
                        for (int j = 1; j <= g; j++)
                            if (i == matcher.start(j) && i != matcher.end(j))
                                System.out.print('(');
                        System.out.print(input.charAt(i));
                        // Print ) for non-empty groups ending here
                        for (int j = 1; j <= g; j++)
                            if (i + 1 != matcher.start(j) && i + 1 == matcher.end(j))
                                System.out.print(')');
                    }
                    System.out.println();
                }
            } else
                System.out.println("No match");
        }
    }
}
