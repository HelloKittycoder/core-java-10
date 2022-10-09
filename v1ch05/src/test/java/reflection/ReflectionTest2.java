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
        // printClassInfo("java.lang.String");
        // printClassInfo("java.util.List");
    }

    public static void printClassInfo(String className) throws ClassNotFoundException {
        StringBuilder sb = new StringBuilder();
        Class<?> clazz = Class.forName(className);
        Class<?> superClazz = clazz.getSuperclass();

        String modifiers = Modifier.toString(clazz.getModifiers());
        if (modifiers.length() > 0) {
            sb.append(modifiers + " ");
        }
        sb.append("class " + className + " ");
        if (superClazz != null && superClazz != Object.class) {
            sb.append("extends " + superClazz.getName() + " ");
        }
        sb.append("{" + LINE_SEPARATOR);

        // 打印构造器
        Constructor<?>[] ctors = clazz.getDeclaredConstructors();
        for (Constructor ctor : ctors) {
            sb.append("\t");
            String ctorModifiers = Modifier.toString(ctor.getModifiers());
            if (ctorModifiers.length() > 0) {
                sb.append(ctorModifiers + " ");
            }
            sb.append(className + "(");
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
            sb.append("\t");
            String methodModifiers = Modifier.toString(m.getModifiers());
            if (methodModifiers.length() > 0) {
                sb.append(methodModifiers + " ");
            }
            sb.append(m.getReturnType().getName() + " ");
            sb.append(m.getName() + "(");
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
            sb.append("\t");
            String fieldModifiers = Modifier.toString(f.getModifiers());
            if (fieldModifiers.length() > 0) {
                sb.append(fieldModifiers + " ");
            }
            sb.append(f.getType().getName() + " ")
               .append(f.getName() + ";")
               .append(LINE_SEPARATOR);
        }
        sb.append("}");

        System.out.println(sb);
    }
}