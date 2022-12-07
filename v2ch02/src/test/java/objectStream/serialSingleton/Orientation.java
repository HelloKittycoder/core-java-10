package objectStream.serialSingleton;

import java.io.InvalidObjectException;
import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * Created by shucheng on 2020/9/22 9:26
 */
public class Orientation implements Serializable {

    public static final Orientation HORIZONTAL = new Orientation(1);
    public static final Orientation VERTICAL = new Orientation(2);

    private int value;

    public Orientation(int value) {
        this.value = value;
    }

    protected Object readResolve() throws ObjectStreamException {
        if (value == 1) return Orientation.HORIZONTAL;
        if (value == 2) return Orientation.VERTICAL;
        throw new InvalidObjectException("value is invalid");
    }
}