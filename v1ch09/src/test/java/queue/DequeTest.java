package queue;

import java.util.Deque;
import java.util.LinkedList;

/**
 * 测试Deque接口的方法
 * Created by shucheng on 10/26/2020 8:53 PM
 */
public class DequeTest {

    public static void main(String[] args) {
        Deque<String> deque = new LinkedList();
        /**
         * 将给定的对象添加到双端队列的头部或尾部。如果队列满了，前面两个方法将抛出一个
         * IllegalStateException，而后面两个方法返回false。
         *
         * 这里使用的LinkedList不会出现满的情况，add和offer在这里没区别
         */
        deque.addFirst("11-addFirst");
        deque.addLast("11-addLast");
        deque.offerFirst("11-offerFirst");
        deque.offerLast("11-offerLast");

        /**
         * 如果队列不空，删除并返回队列头部的元素。如果队列为空，前面两个方法将抛出一个
         * NoSuchElementException，而后面两个方法返回null
         */
        Deque<String> deque2 = new LinkedList();
        try {
            deque2.removeFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            deque2.removeLast();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(deque2.pollFirst());
        System.out.println(deque2.pollLast());

        /**
         * 如果队列非空，返回队列头部的元素，但不删除。如果队列为空，前面两个方法
         * 将抛出一个NoSuchElementException，而后面两个方法返回null
         */
        try {
            deque2.getFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            deque2.getLast();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(deque2.peekFirst());
        System.out.println(deque2.peekLast());
    }
}
