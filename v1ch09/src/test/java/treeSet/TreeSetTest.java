package treeSet;

import java.util.*;

/**
 * 9.2.4 树集
 * This program sorts a set of item by comparing their descriptions.
 *
 * Created by shucheng on 10/25/2020 3:55 PM
 */
public class TreeSetTest {

    public static void main(String[] args) {
        SortedSet<Item> parts = new TreeSet<>();
        parts.add(new Item("Toaster", 1234));
        parts.add(new Item("Widget", 4562));
        parts.add(new Item("Modem", 9912));
        System.out.println(parts);

        /**
         * parts被加到sortByDescription这个Set时，同时按照description进行排序，
         * 但不会影响原始的parts，sortByDescription中放的是排序后的结果
         */
        NavigableSet<Item> sortByDescription = new TreeSet<>(
                Comparator.comparing(Item::getDescription));
        sortByDescription.addAll(parts);
        System.out.println(sortByDescription);
    }
}
