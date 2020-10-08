package jaas;

import javax.swing.*;
import java.awt.*;

/**
 * 9.3.2 JAAS登录模块
 * This program authenticates a user via a custom login and then looks up a system property with
 * the user's privileges.
 *
 * 说明：要运行这个测试的话，
 * 到target/test-classes目录下执行以下命令
 * jar cvf login.jar jaas/JAAS*.class jaas/Simple*.class
 * jar cvf action.jar jaas/SysPropAction.class
 * java -classpath login.jar;action.jar -Djava.security.policy=jaas/JAASTest.policy -Djava.security.auth.login.config=jaas/jaas.config jaas.JAASTest
 *
 * 以上经过尝试发现无法正常获取属性值，这个暂时先跳过，后续有时间再深入
 * （cmd里运行的时候，用harry/secret登录直接报错Exception in thread "AWT-EventQueue-0" java.security.AccessControlException: access denied ("java.util.PropertyPermission" "user.home" "read")
 * 但是JAASTest.policy中明明给admin角色赋予了读取所有属性值的权限，很奇怪）
 * 并没有图9-10（P438）所示的效果
 *
 * Created by shucheng on 2020/10/7 21:28
 */
public class JAASTest {

    public static void main(String[] args) {
        System.setSecurityManager(new SecurityManager());
        EventQueue.invokeLater(() -> {
            JFrame frame = new JAASFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("JAASTest");
            frame.setVisible(true);
        });
    }
}
