package buttons2_1;

import java.awt.*;

/**
 * Created by shucheng on 2020/10/1 11:23
 */
public class ButtonFrameExt extends ButtonFrame {
    @Override
    protected void addEventHandlers() {
        yellowButton.addActionListener(event -> {
            panel.setBackground(Color.YELLOW);
        });

        blueButton.addActionListener(event -> {
            panel.setBackground(Color.BLUE);
        });

        redButton.addActionListener(event -> {
            panel.setBackground(Color.RED);
        });
    }
}
