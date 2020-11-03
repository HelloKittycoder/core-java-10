package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 5.7.3 利用反射分析类的能力
 * This program uses reflection to print all features of a class.
 * Created by shucheng on 2020/10/9 19:08
 */
public class ReflectionTest {

    public static void main(String[] args) throws ClassNotFoundException {
        // Double
        printClassInfo("java.lang.Double");
        // printClassInfo("java.util.List");
    }

    // 自己写的
    public static void printClassInfo(String className) throws ClassNotFoundException {
        try {
            // print class name and superclass name (if != Object)
            Class<?> cl = Class.forName(className);
            Class<?> superCl = cl.getSuperclass();
            String modifiers = Modifier.toString(cl.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.print("class " + className);
            if (superCl != null && superCl != Object.class)
                System.out.print(" extends " + superCl.getName());

            System.out.println(" {");
            printConstructors(cl);
            System.out.println();
            printMethods(cl);
            System.out.println();
            printFields(cl);
            System.out.println("}");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prints all constructors of a class
     * @param cl a class
     */
    public static void printConstructors(Class cl) {
        Constructor[] constructors = cl.getDeclaredConstructors();
        for (Constructor c : constructors) {
            String name = c.getName();
            System.out.print("\t");
            String modifiers = Modifier.toString(c.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.print(name + "(");

            // print parameter types
            Class[] parameterTypes = c.getParameterTypes();
            for (int j = 0; j < parameterTypes.length; j++) {
                if (j > 0) System.out.print(", ");
                System.out.print(parameterTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    /**
     * Prints all methods of a class
     * @param cl a class
     */
    public static void printMethods(Class cl) {
        Method[] methods = cl.getDeclaredMethods();
        for (Method m : methods) {
            Class<?> retType = m.getReturnType();
            String name = m.getName();

            System.out.print("\t");
            // print modifiers, return type and method name
            String modifiers = Modifier.toString(m.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.print(retType.getName() + " " + name + "(");

            // print parameter types
            Class<?>[] paramTypes = m.getParameterTypes();
            for (int j  = 0; j < paramTypes.length; j++) {
                if (j > 0) System.out.print(", ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    /**
     * Prints all fields of a class
     * @param cl a class
     */
    public static void printFields(Class cl) {
        Field[] fields = cl.getDeclaredFields();
        for (Field f : fields) {
            Class<?> type = f.getType();
            String name = f.getName();
            System.out.print("\t");
            String modifiers = Modifier.toString(f.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            System.out.println(type.getName() + " " + name + ";");
        }
    }
}