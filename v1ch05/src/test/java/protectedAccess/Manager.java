package protectedAccess;

/**
 * Created by shucheng on 2020/10/9 10:55
 */
public class Manager extends Employee {

    private double bonus;

    /**
     * @param name the employee's name
     * @param salary the salary
     * @param year the hire year
     * @param month the hire month
     * @param day the hire day
     */
    public Manager(String name, double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
        bonus = 0;
    }

    // 子类可以直接访问父类的protected属性
    @Override
    public double getSalary() {
        // double baseSalary = super.getSalary();
        /**
         * 目前这里使用salary与super.getSalary()都是一样的，但是如果当前类也定义了salary字段时，
         * 这里就必须写成super.getSalary()
         */
        double baseSalary = salary;
        return baseSalary + bonus;
    }

    public void setBonus(double b) {
        bonus = b;
    }
}