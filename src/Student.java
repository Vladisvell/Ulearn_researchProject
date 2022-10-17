import java.util.ArrayList;

public class Student extends Person{

    ArrayList<Module> modules = new ArrayList<Module>();
    private final String group;

    public Student(String name, String group){
        super(name);
        this.group = group;
    }

    @Override
    public String toString() {
        return super.toString() + "\nГруппа: " + group;
    }
}
