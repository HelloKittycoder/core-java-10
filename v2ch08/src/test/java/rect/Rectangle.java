package rect;

import sourceAnnotations.ToString;

/**
 * Created by shucheng on 2020/10/1 21:26
 */
@ToString
public class Rectangle {
    private Point topLeft;
    private int width;
    private int height;

    public Rectangle(Point topLeft, int width, int height) {
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
    }

    @ToString
    public Point getTopLeft() {
        return topLeft;
    }

    @ToString
    public int getWidth() {
        return width;
    }

    @ToString
    public int getHeight() {
        return height;
    }
}
