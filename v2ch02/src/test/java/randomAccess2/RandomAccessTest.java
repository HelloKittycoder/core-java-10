package randomAccess2;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by shucheng on 2020/9/17 22:14
 */
public class RandomAccessTest {

    public static void main(String[] args) throws IOException {

        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Carl Cracker", 75000, 1987, 12, 15);
        staff[1] = new Employee("Harry Hacker", 50000, 1989, 10, 1);
        staff[2] = new Employee("Tony Tester", 40000, 1990, 3, 15);

        Path path = Paths.get("employee.dat");
        ByteBuffer buffer = ByteBuffer.allocate(Employee.RECORD_SIZE);

        try (FileChannel channel = FileChannel.open(path,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            // save all employee records the file employee.dat
            for (Employee e : staff) {
                buffer.clear();
                writeData(buffer, e);
                buffer.flip();
                channel.write(buffer);
            }
        }

        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            // compute the array size
            int n = (int) (Files.size(path) / Employee.RECORD_SIZE);
            Employee[] newStaff = new Employee[n];

            // read employees in reverse order
            for (int i = n - 1; i >= 0; i--) {
                // newStaff[i] = new Employee();
                // lsc注：上面这行没必要写
                channel.position(i * Employee.RECORD_SIZE);
                buffer.clear();
                channel.read(buffer);
                buffer.flip();
                newStaff[i] = readData(buffer);
            }

            // print the newly read employee records
            for (Employee e : newStaff) {
                System.out.println(e);
            }
        }
    }

    /**
     * Writes employee data to a byte buffer
     * @param out the buffer
     * @param e the employee
     */
    private static void writeData(ByteBuffer out, Employee e) {
        String name = e.getName();
        int length = Math.min(name.length(), Employee.NAME_SIZE - 1);
        // for (int i = 0; i < length; i++) out.putChar(name.charAt(i));
        out.asCharBuffer().put(name.substring(0, length)).put('\0');
        out.position(2 * Employee.NAME_SIZE);
        out.putDouble(e.getSalary());
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(e.getHireDay());
        out.putInt(calendar.get(Calendar.YEAR));
        out.putInt(calendar.get(Calendar.MONTH) + 1);
        out.putInt(calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * Reads employee data from a byte buffer
     * @param in the buffer
     * @return the employee
     */
    private static Employee readData(ByteBuffer in) {
        StringBuilder name = new StringBuilder();
        char ch;
        while ((ch = in.getChar()) != '\0') name.append(ch);
        in.position(2 * Employee.NAME_SIZE);
        double salary = in.getDouble();
        int y = in.getInt();
        int m = in.getInt();
        int d = in.getInt();
        // return new Employee(name.toString(), salary, y, m - 1, d);
        // lsc注：上面原书提供的写法，是有问题的，应该传m
        return new Employee(name.toString(), salary, y, m, d);
    }
}
