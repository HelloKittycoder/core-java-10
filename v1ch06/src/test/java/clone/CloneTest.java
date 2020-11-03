package clone;

/**
 * 6.2.3 对象克隆
 * This program demonstrates cloning.
 *
 * Created by shucheng on 2020/10/10 16:49
 */
public class CloneTest {

    public static void main(String[] args) {
        try {
            Employee original = new Employee("John Q. Pbulic", 50000);
            original.setHireDay(2000, 1, 1);
            Employee copy = original.clone();
            copy.raiseSalary(10);
            copy.setHireDay(2002, 12, 31);
            System.out.println("original=" + original);
            System.out.println("copy=" + copy);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }
}