package rect;

import sourceAnnotations.ToString;

/**
 * Created by shucheng on 2020/10/1 21:27
 */
@ToString(includeName = false)
public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @ToString(includeName = false)
    public int getX() {
        return x;
    }

    @ToString(includeName = false)
    public int getY() {
        return y;
    }
}
