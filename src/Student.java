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
        return "Студент " + super.toString() + "\nГруппа: " + group + "\n";
    }

    public String getFullInfo(){
        return toString() + "\n".toString().join("\n", modules.stream().map(module -> module.toString()).toList());
    }
}
