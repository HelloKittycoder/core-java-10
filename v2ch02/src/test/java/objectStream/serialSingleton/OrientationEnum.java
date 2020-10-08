package objectStream.serialSingleton;

/**
 * Created by shucheng on 2020/9/22 18:39
 */
public enum OrientationEnum {

    HORIZONTAL(1), VERTICAL(2);

    int value;

    OrientationEnum(int value) {
        this.value = value;
    }
}