package DefaultPackage;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class StudentUtilities {
    public static List<String> GetAllGroups(List<Student> students){
        HashSet<String> groups = new HashSet<>();
        for(Student student: students){
            if(!groups.contains(student.getGroup())){
                groups.add(student.getGroup());
            }
        }
        return groups.stream().collect(Collectors.toList());
    }
}
