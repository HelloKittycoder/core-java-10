package mycompiler_test;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

/**
 * Created by shucheng on 2020/9/30 9:43
 */
public class StringObject extends SimpleJavaFileObject {

    private String content;

    protected StringObject(String className, String contents) {
        super(URI.create(className), Kind.SOURCE);
        // super(URI.create(className.replace(".", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.content = contents;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return content;
    }
}