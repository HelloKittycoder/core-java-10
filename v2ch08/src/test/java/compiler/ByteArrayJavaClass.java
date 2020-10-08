package compiler;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * A Java class that holds the bytecodes in a byte array.
 * Created by shucheng on 2020/9/29 17:43
 */
public class ByteArrayJavaClass extends SimpleJavaFileObject {

    private ByteArrayOutputStream stream;

    /**
     * Constructs a new ByteArrayJavaClass.
     * @param name the name of the class file represented by this file object
     */
    public ByteArrayJavaClass(String name) {
        super(URI.create("bytes:///" + name), Kind.CLASS);
        stream = new ByteArrayOutputStream();
    }

    @Override
    public OutputStream openOutputStream() throws IOException {
        return stream;
    }

    public byte[] getBytes() {
        return stream.toByteArray();
    }
}