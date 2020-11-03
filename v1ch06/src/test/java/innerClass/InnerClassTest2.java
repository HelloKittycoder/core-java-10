package innerClass;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * 6.4.3 内部类是否有用、必要和安全
 * 这里为了验证书中所说的自己编写程序能否实现内部类引用外围类的机制，
 * 确实如书中所说，是不可以的
 *
 * Created by shucheng on 2020/10/12 13:04
 */
public class InnerClassTest2 {

    public static void main(String[] args) {
        TalkingClock2 clock = new TalkingClock2(1000, true);
        clock.start();

        // keep program running util user selects "Ok"
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

/**
 * A clock that prints the time in regular intervals
 */
class TalkingClock2 {

    private int interval;
    private boolean beep;

    /**
     * Constructs a talking clock
     * @param interval the interval between messages (in milliseconds)
     * @param beep true if the clock should beep
     */
    public TalkingClock2(int interval, boolean beep) {
        this.interval = interval;
        this.beep = beep;
    }

    /**
     * Starts the clock
     */
    public void start() {
        ActionListener listener = new TimerPrinter2(this);
        /**
         * 说明：还可以写成：
         * ActionListener listener = this.new TimerPrinter();
         */
        Timer t = new Timer(interval, listener);
        t.start();
    }
}

class TimerPrinter2 implements ActionListener {

    private TalkingClock2 outer;

    public TimerPrinter2(TalkingClock2 clock) {
        this.outer = clock;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        System.out.println("At the tone, the time is " + new Date());
        // 下面会发现outer.beep编译报错
        // if (outer.beep) Toolkit.getDefaultToolkit().beep();
    }
}