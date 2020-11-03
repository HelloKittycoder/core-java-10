package timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * 6.2.1 接口与回调
 *
 * Created by shucheng on 2020/10/10 16:13
 */
public class TimerTest {

    public static void main(String[] args) {
        ActionListener listener = new TimePrinter();

        // construct a timer that calls the listener
        // once every 10 seconds
        Timer t = new Timer(10000, listener);
        t.start();

        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

class TimePrinter implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("At the tone, the time is " + new Date());
        Toolkit.getDefaultToolkit().beep();
    }
}