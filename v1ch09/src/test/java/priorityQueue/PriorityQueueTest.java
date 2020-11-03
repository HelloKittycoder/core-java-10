package priorityQueue;

import java.time.LocalDate;
import java.util.PriorityQueue;

/**
 * 9.2.5 优先级队列（P371）
 *
 * 优先级队列使用了一个优雅且高效的数据结构，称为堆（heap）。堆是一个可以自我调整的
 * 二叉树，对树执行添加和删除操作，可以让最小的元素移动到根，而不必花费时间对元素进行排序。
 *
 * 使用优先级队列的典型示例是任务调度。每一个任务有一个优先级，任务以随机顺序添加到队列中。
 * 每当启动一个新的任务时，都将优先级最高的任务从队列中删除（由于习惯上将1设为“最高”优先级，
 * 所以会将最小的元素删除）。
 *
 * Created by shucheng on 10/26/2020 9:07 PM
 */
public class PriorityQueueTest {

    public static void main(String[] args) {
        PriorityQueue<LocalDate> pq = new PriorityQueue<>();
        pq.add(LocalDate.of(1906, 12, 9)); // G. Hopper
        pq.add(LocalDate.of(1815, 12, 10)); // A. Lovelace
        pq.add(LocalDate.of(1903, 12, 3)); // J. von Neumann
        pq.add(LocalDate.of(1910, 6, 22)); // K. Zuse

        System.out.println("Iterating over elements...");
        for (LocalDate date : pq) {
            System.out.println(date);
        }

        System.out.println("Removing elements...");
        while (!pq.isEmpty()) {
            System.out.println(pq.remove());
        }
    }
}
