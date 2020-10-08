package objectStream.modifySerial;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by shucheng on 2020/9/22 8:00
 */
public class LabelPoint implements Serializable {

    private String label;
    private transient Point point;

    public LabelPoint(String label, Point point) {
        this.label = label;
        this.point = point;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeDouble(point.getX());
        out.writeDouble(point.getY());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        double x = in.readDouble();
        double y = in.readDouble();
        point = new Point(x, y);
    }

    @Override
    public String toString() {
        return getClass().getName()+ "{" +
                "label='" + label + '\'' +
                ", point=" + point +
                '}';
    }

    public static class Point {
        private double x;
        private double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        @Override
        public String toString() {
            return getClass().getName() + "{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
