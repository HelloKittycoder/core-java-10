package wildcard;

/**
 * 8.8.1 通配符概念
 * Created by shucheng on 2020/10/15 13:36
 */
public class WildcardTest {

    public static Pair<? extends Employee> printBuddies(Pair<? extends Employee> p) {
        Employee first = p.getFirst();
        Employee second = p.getSecond();
        System.out.println(first.getName() + " and " + second.getName() + " are buddies.");
        Pair<Manager> pm = new Pair<>();
        return pm;
    }

    public static void main(String[] args) {
        Manager m1 = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        Manager m2 = new Manager("Carl Cracker2", 80000, 1987, 12, 15);
        Pair<Manager> managerBuddies = new Pair<>(m1, m2);
        printBuddies(managerBuddies);

        // Pair<? extends Employee> wildcardBuddies = managerBuddies; // OK

        /*Employee e1 = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        wildcardBuddies.setFirst(e1);*/
    }
}