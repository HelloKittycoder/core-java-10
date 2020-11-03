package serviceLoader2.impl;

import serviceLoader2.IService;

/**
 * Created by shucheng on 2020/10/29 17:32
 */
public class LocalService implements IService {
    @Override
    public String sayHello() {
        return "Hello LocalService";
    }

    @Override
    public String getScheme() {
        return "local";
    }
}