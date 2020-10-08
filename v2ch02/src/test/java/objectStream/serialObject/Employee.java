package objectStream.serialObject;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by shucheng on 2020/9/21 22:08
 */
public class Employee implements Serializable {

    private String name;
    private double salary;
    private LocalDate hireDay;

    public Employee() {}

    public Employee(String n, double s, int year, int month, int day) {
        name = n;
        salary = s;
        hireDay = LocalDate.of(year, month, day);
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getHireDay() {
        return hireDay;
    }

    /**
     * Raises the salary of this employee.
     * @param byPercent the percentage of the raise
     */
    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    @Override
    public String toString() {
        return getClass().getName() +"{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", hireDay=" + hireDay +
                '}';
    }
}
