public class TaskInfo {
    String taskName;
    float maxPoints;
    float actualPoints;

    public TaskInfo(String taskName, float maxPoints, float actualPoints){
        this.taskName = taskName;
        this.maxPoints = maxPoints;
        this.actualPoints = actualPoints;
    }

    @Override
    public String toString() {
        return String.format("%s - %s баллов из %s возможных",taskName,actualPoints,maxPoints);
    }
}
