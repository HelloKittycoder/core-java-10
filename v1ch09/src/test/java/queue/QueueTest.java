package queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 测试Queue接口的方法
 * Created by shucheng on 10/26/2020 7:14 PM
 */
public class QueueTest {

    public static void main(String[] args) {
        Queue<String> queue = new LinkedList<>();
        /**
         * add和offer：如果队列没有满，将给定的元素添加到这个双端队列的尾部并返回true；
         * 如果队列满了，第一个方法将抛出一个IllegalStateException，而第二个方法返回false
         *
         * 这里使用的LinkedList不会出现满的情况，add和offer在这里没区别
         */
        queue.add("11-add");
        queue.offer("11-offer");
        System.out.println(queue);

        /**
         * remove和poll：如果队列不空，删除并返回这个队列头部的元素。如果队列是空的，
         * 第一个方法抛出NoSuchElementException，第二个方法返回null
         */
        Queue queue1 = new LinkedList();
        try {
            Object removedObj = queue1.remove();
            System.out.println(removedObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Object polledObj = queue1.poll();
        System.out.println(polledObj);

        /**
         * 假如队列不空，删除并返回这个队列头部的元素。如果队列是空的，
         * 第一个方法抛出NoSuchElementException，第二个方法返回null
         */
        Object element = queue.element();
        System.out.println(element);
        Object peek = queue.peek();
        System.out.println(peek);

        try {
            Object elementObj = queue1.element();
            System.out.println(elementObj);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Object peekObj = queue1.peek();
        System.out.println(peekObj);
    }
}
