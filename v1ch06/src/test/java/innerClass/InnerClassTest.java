package innerClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * 6.4.1 使用内部类访问对象状态
 * This program demonstrates the use of inner classes
 *
 * Created by shucheng on 2020/10/12 13:04
 */
public class InnerClassTest {

    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock(1000, true);
        clock.start();

        // keep program running util user selects "Ok"
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

/**
 * A clock that prints the time in regular intervals
 */
class TalkingClock {

    private int interval;
    private boolean beep;

    /**
     * Constructs a talking clock
     * @param interval the interval between messages (in milliseconds)
     * @param beep true if the clock should beep
     */
    public TalkingClock(int interval, boolean beep) {
        this.interval = interval;
        this.beep = beep;
    }

    /**
     * Starts the clock
     */
    public void start() {
        ActionListener listener = new TimerPrinter();
        /**
         * 说明：还可以写成：
         * ActionListener listener = this.new TimerPrinter();
         */
        Timer t = new Timer(interval, listener);
        t.start();
    }

    public class TimerPrinter implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            System.out.println("At the tone, the time is " + new Date());
            // 说明：beep还可以写成 TalkingClock.this.beep
            if (beep) Toolkit.getDefaultToolkit().beep();
        }
    }
}