package interruptible;

import javax.swing.*;
import java.awt.*;

/**
 * 4.3 可中断套接字
 * 这一部分只是敲了代码，后续再深入
 * Created by shucheng on 2020/9/26 13:55
 */
public class InterruptibleSocketTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new InterruptibleSocketFrame();
            frame.setTitle("InterruptibleSocketTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
