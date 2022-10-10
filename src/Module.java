import java.util.ArrayList;

public class Module {
    String name;
    float practiceMaxPoints;
    float homeworkMaxPoints;
    ArrayList<TaskInfo> practices = new ArrayList<>();
    ArrayList<TaskInfo> homeworks = new ArrayList<>();

    TaskInfo getTaskByName(String taskName){
        return new TaskInfo();
    }
}
