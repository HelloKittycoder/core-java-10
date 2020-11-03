package localInnerClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * 6.4.4 局部内部类
 * This program demonstrates the use of local inner classes.
 *
 * Created by shucheng on 2020/10/12 13:04
 */
public class LocalInnerClassTest {

    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock();
        clock.start(1000, true);

        // keep program running util user selects "Ok"
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

/**
 * A clock that prints the time in regular intervals
 */
class TalkingClock {

    /**
     * Starts the clock.
     * @param interval the interval between messages (in milliseconds)
     * @param beep true if the clock should beep
     */
    public void start(int interval, boolean beep) {
        class TimerPrinter implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent event) {
                System.out.println("At the tone, the time is " + new Date());
                if (beep) Toolkit.getDefaultToolkit().beep();
            }
        }
        ActionListener listener = new TimerPrinter();
        Timer t = new Timer(interval, listener);
        t.start();
    }
}