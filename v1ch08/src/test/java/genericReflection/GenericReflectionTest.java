package genericReflection;

import java.lang.reflect.*;
import java.util.Arrays;

/**
 * @version 1.10 2007-05-15
 * @author Cay Horstmann
 */
public class GenericReflectionTest
{
   public static void main(String[] args)
   {
      // read class name from command line args or user input
      // pair2.ArrayAlg；java.util.ArrayList
      // String name = "pair2.ArrayAlg";
      String name = "java.util.ArrayList";
      /*if (args.length > 0) name = args[0];
      else
      {
         try (Scanner in = new Scanner(System.in))
         {
            System.out.println("Enter class name (e.g. java.util.Collections): ");
            name = in.next();
         }
      }*/

      try
      {
         // print generic info for class and public methods
         Class<?> cl = Class.forName(name);
         // 打印class声明
         printClass(cl);
         for (Method m : cl.getDeclaredMethods())
            // 打印方法声明
            printMethod(m);
      }
      catch (ClassNotFoundException e)
      {
         e.printStackTrace();
      }
   }

   public static void printClass(Class<?> cl)
   {
      System.out.print(cl);
      // 打印类型参数
      printTypes(cl.getTypeParameters(), "<", ", ", ">", true);
      // 打印extends部分
      Type sc = cl.getGenericSuperclass();
      if (sc != null)
      {
         System.out.print(" extends ");
         printType(sc, false);
      }
      // 打印implements部分
      printTypes(cl.getGenericInterfaces(), " implements ", ", ", "", false);
      System.out.println();
   }

   public static void printMethod(Method m)
   {
      String name = m.getName();
      // 打印修饰符
      System.out.print(Modifier.toString(m.getModifiers()));
      System.out.print(" ");
      // 打印类型参数
      printTypes(m.getTypeParameters(), "<", ", ", "> ", true);

      // 打印带泛型的返回值
      printType(m.getGenericReturnType(), false);
      System.out.print(" ");
      // 打印方法名
      System.out.print(name);
      System.out.print("(");
      // 打印泛型参数
      printTypes(m.getGenericParameterTypes(), "", ", ", "", false);
      System.out.println(")");
   }

   public static void printTypes(Type[] types, String pre, String sep, String suf,
         boolean isDefinition)
   {
      if (pre.equals(" extends ") && Arrays.equals(types, new Type[] { Object.class })) return;
      if (types.length > 0) System.out.print(pre);
      for (int i = 0; i < types.length; i++)
      {
         if (i > 0) System.out.print(sep);
         printType(types[i], isDefinition);
      }
      if (types.length > 0) System.out.print(suf);
   }

   public static void printType(Type type, boolean isDefinition)
   {
      if (type instanceof Class)
      {
         // 描述具体类型
         Class<?> t = (Class<?>) type;
         System.out.print(t.getName());
      }
      else if (type instanceof TypeVariable)
      {
         // 描述类型变量（如 T extends Comparable<? super T> 中的T）
         TypeVariable<?> t = (TypeVariable<?>) type;
         System.out.print(t.getName());
         if (isDefinition)
            printTypes(t.getBounds(), " extends ", " & ", "", false);
      }
      else if (type instanceof WildcardType)
      {
         // 描述通配符（如 ? super T）
         WildcardType t = (WildcardType) type;
         System.out.print("?");
         printTypes(t.getUpperBounds(), " extends ", " & ", "", false);
         printTypes(t.getLowerBounds(), " super ", " & ", "", false);
      }
      else if (type instanceof ParameterizedType)
      {
         // 描述泛型类或接口类型（如 ArrayList<E>，List<E>）
         ParameterizedType t = (ParameterizedType) type;
         Type owner = t.getOwnerType();
         if (owner != null)
         {
            printType(owner, false);
            System.out.print(".");
         }
         printType(t.getRawType(), false);
         printTypes(t.getActualTypeArguments(), "<", ", ", ">", false);
      }
      else if (type instanceof GenericArrayType)
      {
         // 描述泛型数组（如 T[]）
         GenericArrayType t = (GenericArrayType) type;
         System.out.print("");
         printType(t.getGenericComponentType(), isDefinition);
         System.out.print("[]");
      }
   }
}
