package jaas;

import javax.security.auth.callback.*;
import java.io.IOException;

/**
 * This simple callback handler presents the given user name and password.
 * Created by shucheng on 2020/10/7 20:19
 */
public class SimpleCallbackHandler implements CallbackHandler {

    private String username;
    private char[] password;

    /**
     * Constructs the callback handler.
     * @param username the user name
     * @param password a character array containing the password
     */
    public SimpleCallbackHandler(String username, char[] password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for (Callback callback : callbacks) {
            if (callback instanceof NameCallback) {
                ((NameCallback) callback).setName(username);
            } else if (callback instanceof PasswordCallback) {
                ((PasswordCallback) callback).setPassword(password);
            }
        }
    }
}
