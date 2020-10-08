package policyFile;

import java.net.URL;

/**
 * 9.2.3 安全策略文件
 * 简单测试下，暂时没有深入
 * Created by shucheng on 2020/10/7 12:09
 */
public class PolicyFileTest {

    public static void main(String[] args) {
        // 下面如果更换顺序的话，ClassLoader.getSystemResource获取的是null，代码无法继续下去

        // 1.指定自定义的安全策略文件
        URL policyFile = ClassLoader.getSystemResource("policyFile/MyApp.policy");
        String policyFilePath = policyFile.getPath();
        System.setProperty("java.security.policy", policyFilePath);

        // 2.启用安全管理器
        System.setSecurityManager(new SecurityManager());

        String java_version = System.getProperty("java.version");
        System.out.println(java_version);

        // 这里会抛异常
        System.setProperty("java.version", "1.2.3");
    }
}
