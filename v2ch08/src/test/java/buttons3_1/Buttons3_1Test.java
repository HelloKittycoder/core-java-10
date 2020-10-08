package buttons3_1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import runtimeAnnotations.ActionListenerFor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 8.3.2的辅助理解代码
 * Created by shucheng on 2020/10/1 11:38
 */
public class Buttons3_1Test {

    private static final Logger LOGGER  = LoggerFactory.getLogger(Buttons3_1Test.class);

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ButtonFrame frame = new ButtonFrame();
            frame.setTitle("ButtonTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

    public static void processAnnotations(Object obj) {
        try {
            // 将所有带ActionListenerFor注解的方法加到对应button按钮的listener中
            LOGGER.info("==========处理注解begin==========");
            Class<?> cl = obj.getClass();
            for (Method m : cl.getDeclaredMethods()) {
                ActionListenerFor a = m.getAnnotation(ActionListenerFor.class);
                if (a != null) {
                    Field f = cl.getDeclaredField(a.source());
                    f.setAccessible(true);
                    addListener(f.get(obj), obj, m);
                    LOGGER.info("对{}添加监听方法{}", f.getName(), m);
                }
            }
            LOGGER.info("==========处理注解end==========");
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    public static void addListener(Object source, Object param, Method m) throws ReflectiveOperationException {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                LOGGER.debug("调用{}的{}方法", param.getClass(), m);
                return m.invoke(param);
            }
        };
        LOGGER.debug("创建代理处理器{}==>{}.invoke({})", handler, m.getName(), param.getClass().getName());

        Object listener = Proxy.newProxyInstance(null, new Class[]{ActionListener.class}, handler);
        LOGGER.debug("将代理处理器绑定到新生成的监听器接口{}代理{}中", ActionListener.class, listener.getClass());
        Method adder = source.getClass().getMethod("addActionListener", ActionListener.class);
        adder.invoke(source, listener);
        LOGGER.debug("调用{}的{}方法添加一个被代理的监听器", source.getClass(), "addActionListener");
    }
}
