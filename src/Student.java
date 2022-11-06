import java.util.ArrayList;
import java.util.HashMap;

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

    public static Student createStudent(String[] studentEntries, HashMap<String, ArrayList<CSVUtils.recordID>> categories, String[] ideal){
        Student student = new Student(studentEntries[0], studentEntries[1]);
        for (String category: categories.keySet()){
            if(!category.equals("\uFEFF")) {
                if(!category.equals("Преподавателю о курсе"))
                    student.modules.add(Module.createModule(category, studentEntries, categories, ideal));
            }
        }
        return student;
    }
}
