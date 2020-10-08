package buttons3;

import javax.swing.*;
import java.awt.*;

/**
 * 8.3.2 一个示例：注解事件处理器
 * Created by shucheng on 2020/10/1 10:44
 */
public class ButtonTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ButtonFrame frame = new ButtonFrame();
            frame.setTitle("ButtonTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
