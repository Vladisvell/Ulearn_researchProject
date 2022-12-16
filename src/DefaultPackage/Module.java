package DefaultPackage;

import CSVUtilities.CSVUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Module {
    private String name;
    private float practicePoints = 0;
    private float homeworkPoints = 0;
    private float maxPracticePoints = 0;
    private float maxHomeworkPoints = 0;
    private float activitiesPoints = 0;
    private float maxActivitiesPoints = 0;

    public float getActivitiesPoints() {
        return activitiesPoints;
    }

    public String getName() {
        return name;
    }

    public ArrayList<TaskInfo> getTasks() {
        return tasks;
    }

    public float getHomeworkPoints() {
        return homeworkPoints;
    }

    public float getMaxActivitiesPoints() {
        return maxActivitiesPoints;
    }

    public float getMaxHomeworkPoints() {
        return maxHomeworkPoints;
    }

    public float getMaxPracticePoints() {
        return maxPracticePoints;
    }

    public float getPracticePoints() {
        return practicePoints;
    }

    //ArrayList<DefaultPackage.TaskInfo> practices = new ArrayList<>();
    //ArrayList<DefaultPackage.TaskInfo> homeworks = new ArrayList<>();
    ArrayList<TaskInfo> tasks = new ArrayList<>();

    TaskInfo getTaskByName(String taskName){
        TaskInfo task = tasks.stream().filter(x -> x.taskName.equals(taskName)).findAny().orElse(null);
        if(task == null)
            throw new IllegalArgumentException(String.format("Задания %s не существует в категории %s!",taskName,name));
        else
            return task;
    }

    void assignPoints(){
        TaskInfo homework = tasks.stream().filter(x -> x.taskName.equals("ДЗ")).findAny().orElse(null);
        if(homework != null){
            homeworkPoints = homework.actualPoints;
            maxHomeworkPoints = homework.maxPoints;
        }
        TaskInfo activities = tasks.stream().filter(x -> x.taskName.equals("Акт")).findAny().orElse(null);
        if(activities != null){
            maxActivitiesPoints = activities.maxPoints;
            activitiesPoints = activities.actualPoints;
        }
        TaskInfo practices = tasks.stream().filter(x -> x.taskName.equals("Упр")).findAny().orElse(null);
        if(practices != null){
            practicePoints = practices.actualPoints;
            maxPracticePoints = practices.maxPoints;
        }
    }

    public List<String> getFormattedTasks(){
        List<String> formatted = tasks.stream().map(x -> x.toString()).toList();
        return formatted;
    }

    private String tasksToString(List<String> formattedTasks){
        StringBuilder finalString = new StringBuilder();
        for(String string : formattedTasks){
            finalString.append("\t" + string + "\n");
        }
        return finalString.toString();
    }
    @Override
    public String toString() {
        return String.format("Модуль %s: \n%s",name,tasksToString(getFormattedTasks()));
    }

    public static Module createModule(){
        return new Module();
    }

    public static Module createModule(String categoryName, String[] studentEntries, HashMap<String, ArrayList<CSVUtils.recordID>> categories, String[] ideal){
        Module module = new Module();
        module.name = categoryName;
        ArrayList<CSVUtils.recordID> category = (ArrayList<CSVUtils.recordID>) categories.get(categoryName);
        for(CSVUtils.recordID record : category){
            TaskInfo task = new TaskInfo(record.getInfo(), Float.valueOf(ideal[record.getId()]),Float.valueOf(studentEntries[record.getId()]));
            module.tasks.add(task);
        }
        module.assignPoints();
        return module;
    }
}
