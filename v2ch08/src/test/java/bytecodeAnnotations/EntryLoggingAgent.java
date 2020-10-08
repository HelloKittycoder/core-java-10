package bytecodeAnnotations;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.Instrumentation;

/**
 * Created by shucheng on 2020/10/2 12:59
 */
public class EntryLoggingAgent {

    public static void premain(String arg, Instrumentation instr) {
        instr.addTransformer((loader, className, cl, pd, data) -> {
            if (!className.replace("/", ".").equals(arg)) return null;
            ClassReader reader = new ClassReader(data);
            ClassWriter writer = new ClassWriter(
                    ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            EntryLogger el = new EntryLogger(writer, className);
            reader.accept(el, ClassReader.EXPAND_FRAMES);
            return writer.toByteArray();
        });
    }
}
