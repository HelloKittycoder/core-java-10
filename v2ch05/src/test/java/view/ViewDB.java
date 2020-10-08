package view;

import javax.swing.*;
import java.awt.*;

/**
 * 5.8 元数据
 *
 * 说明：执行这个类的main方法之前，需要先执行ExecSQL的main方法，
 * 执行建表语句，并插入数据
 *
 * Created by shucheng on 2020/9/29 9:58
 */
public class ViewDB {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new ViewDBFrame();
            frame.setTitle("ViewDB");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}