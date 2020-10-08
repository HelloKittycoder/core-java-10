package compiler;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

/**
 * A Java source that holds the code in a string builder.
 * Created by shucheng on 2020/9/29 17:31
 */
public class StringBuilderJavaSource extends SimpleJavaFileObject {

    private StringBuilder code;

    public static void main(String[] args) {
        /*String name = "x.Frame";
        URI uri = URI.create("string:///" + name.replace(".", "/") + Kind.SOURCE.extension);
        System.out.println(uri);*/
        System.out.println(Kind.SOURCE.extension);
    }

    /**
     * Constructs a new StringBuilderJavaSource
     * @param name the name of the source file represented by this object
     */
    public StringBuilderJavaSource(String name) {
        super(URI.create("string:///" + name.replace(".", "/") + Kind.SOURCE.extension),
                Kind.SOURCE);
        code = new StringBuilder();
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return code;
    }

    public void append(String str) {
        code.append(str);
        code.append(System.lineSeparator());
    }
}