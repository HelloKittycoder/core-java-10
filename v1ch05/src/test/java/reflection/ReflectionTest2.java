package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 结合ReflectionTest中的思路，自己重新写了下
 * Created by shucheng on 2020/10/9 19:08
 */
public class ReflectionTest2 {

    public static final String LINE_SEPARATOR = System.lineSeparator();

    public static void main(String[] args) throws ClassNotFoundException {
        // Double
        printClassInfo("java.lang.Double");
        // printClassInfo("java.util.List");
    }

    public static void printClassInfo(String className) throws ClassNotFoundException {
        StringBuilder sb = new StringBuilder();
        Class<?> clazz = Class.forName(className);
        Class<?> superClazz = clazz.getSuperclass();

        String modifiers = Modifier.toString(clazz.getModifiers());
        sb.append(modifiers + className);
        if (superClazz != null && superClazz != Object.class) {
            sb.append(" extends " + superClazz.getName() + " {").append(LINE_SEPARATOR);
        }

        // 打印构造器
        Constructor<?>[] ctors = clazz.getConstructors();
        for (Constructor ctor : ctors) {
            String ctorModifiers = Modifier.toString(ctor.getModifiers());
            sb.append("\t").append(ctorModifiers + " " + className + "(");
            if (ctor.getParameterCount() > 0) {
                Class[] parameterTypes = ctor.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    sb.append(parameterTypes[i].getName());
                }
            }
            sb.append(");").append(LINE_SEPARATOR);
        }
        sb.append(LINE_SEPARATOR);

        // 打印方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method m : methods) {
            String methodModifiers = Modifier.toString(m.getModifiers());
            sb.append("\t").append(methodModifiers + " " + m.getReturnType().getName() + " " + m.getName() + "(");
            if (m.getParameterCount() > 0) {
                Class<?>[] parameterTypes = m.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (i != 0) {
                        sb.append(", ");
                    }
                    sb.append(parameterTypes[i].getName());
                }
            }
            sb.append(");").append(LINE_SEPARATOR);
        }
        sb.append(LINE_SEPARATOR);

        // 打印属性
        Field[] fields = clazz.getDeclaredFields();
        for (Field f : fields) {
            String fieldModifiers = Modifier.toString(f.getModifiers());
            sb.append("\t").append(fieldModifiers + " " + f.getType().getName() + " " + f.getName() + ";")
                .append(LINE_SEPARATOR);
        }
        sb.append("}");

        System.out.println(sb);
    }
}