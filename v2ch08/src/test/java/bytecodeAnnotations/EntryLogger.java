package bytecodeAnnotations;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.AdviceAdapter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Adds "entering" logs to all methods of a class that have the LogEntry annotation.
 * Created by shucheng on 2020/10/2 9:00
 */
public class EntryLogger extends ClassVisitor {

    private String className;

    /**
     * Constructs an EntryLogger that inserts logging into annotated methods of a given class.
     */
    public EntryLogger(ClassWriter writer, String className) {
        super(Opcodes.ASM5, writer);
        this.className = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String methodName, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, methodName, desc, signature, exceptions);
        return new AdviceAdapter(Opcodes.ASM5, mv, access, methodName, desc) {
            private String loggerName;

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                return new AnnotationVisitor(Opcodes.ASM5) {
                    @Override
                    public void visit(String name, Object value) {
                        if (desc.equals("LbytecodeAnnotations/LogEntry;") && name.equals("logger")) {
                            loggerName = value.toString();
                        }
                    }
                };
            }

            @Override
            protected void onMethodEnter() {
                if (loggerName != null) {
                    visitLdcInsn(loggerName);
                    visitMethodInsn(INVOKESTATIC, "java/util/logging/Logger", "getLogger",
                            "(Ljava/lang/String;)Ljava/util/logging/Logger;", false);
                    visitLdcInsn(className);
                    visitLdcInsn(methodName);
                    visitMethodInsn(INVOKEVIRTUAL, "java/util/logging/Logger", "entering",
                            "(Ljava/lang/String;Ljava/lang/String;)V", false);
                    loggerName = null;
                }
            }
        };
    }

    /**
     * Adds entry logging code to the given class.
     * @param args the name of the class file to patch
     */
    public static void main(String[] args) throws IOException, URISyntaxException {
        if (args.length == 0) {
            /*System.out.println("USAGE: java bytecodeAnnotations.EntryLogger classfile");
            System.exit(1);*/
            args = new String[]{"set\\Item.class"};
        }

        String pathStr = args[0];
        URL systemResource = ClassLoader.getSystemResource(pathStr);
        Path path = Paths.get(systemResource.toURI());
        ClassReader reader = new ClassReader(Files.newInputStream(path));
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
        EntryLogger entryLogger = new EntryLogger(writer,
                pathStr.replace(".class", "").replaceAll("[/\\\\]", "."));
        reader.accept(entryLogger, ClassReader.EXPAND_FRAMES);
        Files.write(path, writer.toByteArray());
    }
}
