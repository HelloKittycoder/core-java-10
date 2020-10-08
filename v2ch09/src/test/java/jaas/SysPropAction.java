package jaas;

import java.security.PrivilegedAction;

/**
 * This action looks up a system property.
 * Created by shucheng on 2020/10/7 16:32
 */
public class SysPropAction implements PrivilegedAction<String> {

    private String propertyName;

    /**
     * Constructs an action for looking up a given property.
     * @param propertyName the property name(such as "user.home")
     */
    public SysPropAction(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String run() {
        return System.getProperty(propertyName);
    }
}
