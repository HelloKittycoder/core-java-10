package oldCollection;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 9.6.3 属性映射
 * Created by shucheng on 2020/10/28 23:28
 */
public class PropertiesTest {

    @Test
    public void test() throws IOException {
        Properties properties = new Properties();
        Object aaa = properties.setProperty("aaa", "111");
        System.out.println(aaa); // 返回的是原来aaa对应的value，因为原本没有aaa这个属性，这里返回的是null
        InputStream inputStream = ClassLoader.getSystemResourceAsStream("oldCollection/myconfig.properties");
        properties.load(inputStream);
        inputStream.close();
        System.out.println(properties);
        System.out.println(properties.get("url"));
    }
}
