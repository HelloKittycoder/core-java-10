package protectedAccess;


/**
 * 5.1.10 受保护访问
 * Created by shucheng on 2020/10/9 13:40
 */
public class ProtectedTest {

    public static void main(String[] args) {
        // construct a Manager object
        Manager boss = new Manager("Carl Cracker", 80000, 1987, 12, 15);
        boss.setBonus(5000);

        // 同包下可以访问protected属性
        System.out.println(boss.salary);

        Employee[] staff = new Employee[3];

        // fill the staff array with Manager and Employee objects
        staff[0] = boss;
        staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee("Tommy Tester", 40000, 1990, 3, 15);

        // print out information about all Employee objects
        for (Employee e : staff)
            System.out.println("name=" + e.getName() + ",salary=" + e.getSalary());
    }
}