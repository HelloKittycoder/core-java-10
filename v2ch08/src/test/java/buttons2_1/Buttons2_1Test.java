package buttons2_1;

import javax.swing.*;
import java.awt.*;

/**
 * 为了更好的理解8.2.3的内容，我把不使用动态Java代码生成时的写法写了下
 * Created by shucheng on 2020/10/1 11:25
 */
public class Buttons2_1Test {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ButtonFrameExt fe = new ButtonFrameExt();
            fe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fe.setTitle("CompilerTest");
            fe.setVisible(true);
        });
    }
}
