package auth;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

/**
 * 9.3.1 JAAS框架
 * This program authenticates a user via a custom login and then executes the SysPropAction with th
 * user's privileges.
 *
 * 说明：要运行这个测试的话，
 * 到target/test-classes目录下执行以下命令
 * jar cvf login.jar auth/AuthTest.class
 * jar cvf action.jar auth/SysPropAction.class
 * java -classpath login.jar;action.jar -Djava.security.policy=auth/AuthTest.policy -Djava.security.auth.login.config=auth/jaas.config auth.AuthTest
 *
 * Created by shucheng on 2020/10/7 16:26
 */
public class AuthTest {

    public static void main(String[] args) {
        System.setSecurityManager(new SecurityManager());
        try {
            LoginContext context = new LoginContext("Login1");
            context.login();
            System.out.println("Authentication successful.");
            Subject subject = context.getSubject();
            System.out.println("subject=" + subject);
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
