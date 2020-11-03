package oldCollection;

import java.util.Stack;

/**
 * 9.6.4 栈
 * Created by shucheng on 2020/10/28 23:38
 */
public class StackTest {

    public static void main(String[] args) {
        Stack<String> stack = new Stack<>();
        System.out.println(stack.empty());

        // 入栈
        stack.push("11");
        stack.push("22");
        stack.push("33");

        // 查看栈顶元素
        String stackTopElement = stack.peek();
        System.out.println(stackTopElement);

        // 出栈
        while (!stack.empty()) {
            String poppedElement = stack.pop();
            System.out.println("元素" + poppedElement + "出栈");
        }
        System.out.println("所有元素出栈完毕！");
    }
}
