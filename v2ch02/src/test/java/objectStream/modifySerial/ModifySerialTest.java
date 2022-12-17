package objectStream.modifySerial;

import common.util.FileUtil;
import org.junit.Test;

import java.io.*;

/**
 * 2.4.3 修改默认的序列化机制
 * Created by shucheng on 2020/9/22 8:02
 */
public class ModifySerialTest {

    @Test
    public void test() throws IOException, ClassNotFoundException {
        LabelPoint.Point point = new LabelPoint.Point(1, 2);
        LabelPoint labelPoint = new LabelPoint("11", point);

        String path = FileUtil.getResourcePath("objectStream/modifySerial/labelPoint.dat");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))) {
            out.writeObject(labelPoint);
        }

        try (ObjectInputStream in  = new ObjectInputStream(new FileInputStream(path))) {
            LabelPoint newLabelPoint = (LabelPoint) in.readObject();
            System.out.println(newLabelPoint);
        }
    }
}
