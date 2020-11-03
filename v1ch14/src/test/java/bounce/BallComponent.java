package bounce;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The component that draws the balls.
 * Created by shucheng on 2020/10/29 18:47
 */
public class BallComponent extends JPanel {

    public static final int DEFAULT_WIDTH = 450;
    public static final int DEFAULT_HEIGHT = 350;

    private List<Ball> balls = new ArrayList<>();

    /**
     * Add a ball to the component.
     * @param b the ball to add
     */
    public void add(Ball b) {
        balls.add(b);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); // erase background
        Graphics2D g2 = (Graphics2D) g;
        for (Ball b : balls) {
            g2.fill(b.getShape());
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }
}