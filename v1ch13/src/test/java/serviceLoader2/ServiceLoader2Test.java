package serviceLoader2;

import java.util.ServiceLoader;

/**
 * 网上找了一个serviceLoader的例子
 * 参考链接：https://www.cnblogs.com/aspirant/p/10616704.html
 *
 * Created by shucheng on 2020/10/29 17:33
 */
public class ServiceLoader2Test {

    public static void main(String[] args) {
        ServiceLoader<IService> serviceLoader = ServiceLoader.load(IService.class);
        for (IService service : serviceLoader) {
            System.out.println(service.getScheme() + "=" + service.sayHello());
        }
    }
}