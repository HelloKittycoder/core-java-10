package genericReflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * 在没看GenericReflectionTest的情况下写的，这里考虑的情况比较简单
 * Created by shucheng on 2020/10/21 12:30
 */
public class GenericReflectionTest2 {

    public static void main(String[] args) throws Exception {
        String className = "pair2.ArrayAlg";

        Class<?> clazz = Class.forName(className);
        Class<?> superclazz = clazz.getSuperclass();
        // 打印类的声明
        System.out.println("class " + className + " extends " + superclazz.getName());

        // 打印方法声明
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method m : declaredMethods) {
            String modifiers = Modifier.toString(m.getModifiers());
            Type genericReturnType = m.getGenericReturnType();

            TypeVariable<Method>[] typeParameters = m.getTypeParameters();
            String typeName = typeParameters[0].getBounds()[0].getTypeName();
            System.out.println(modifiers + " <" + typeParameters[0].getName() + " extends "
                    + typeName +"> " + genericReturnType + " "
                    + m.getName() + "(" + m.getGenericParameterTypes()[0] + ")");
        }
    }
}