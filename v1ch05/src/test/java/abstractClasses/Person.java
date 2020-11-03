package abstractClasses;

/**
 * Created by shucheng on 2020/10/9 13:20
 */
public abstract class Person {

    public abstract String getDescription();
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}