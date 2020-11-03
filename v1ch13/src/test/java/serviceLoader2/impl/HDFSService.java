package serviceLoader2.impl;

import serviceLoader2.IService;

/**
 * Created by shucheng on 2020/10/29 17:31
 */
public class HDFSService implements IService {
    @Override
    public String sayHello() {
        return "Hello HDFSService";
    }

    @Override
    public String getScheme() {
        return "hdfs";
    }
}