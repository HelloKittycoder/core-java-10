package map;

/**
 * A minimalist employee class for testing purposes.
 * Created by shucheng on 10/26/2020 9:26 PM
 */
public class Employee {

    private String name;
    private double salary;

    /**
     * Constructs an employee with $0 salary.
     * @param name the employee name
     */
    public Employee(String name) {
        this.name = name;
        salary = 0;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
