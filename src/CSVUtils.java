import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class CSVUtils {
    public static Module createModule(String categoryName, String[] studentEntries, HashMap<String, ArrayList<recordID>> categories, String[] ideal){
        Module module = new Module();
        module.name = categoryName;
        ArrayList<recordID> category = (ArrayList<recordID>) categories.get(categoryName);
        for(recordID record : category){
            TaskInfo task = new TaskInfo(record.info, Float.valueOf(ideal[record.id]),Float.valueOf(studentEntries[record.id]));
            module.tasks.add(task);
        }
        module.assignPoints();
        return module;
    }

    public static Student createStudent(String[] studentEntries, HashMap<String, ArrayList<recordID>> categories, String[] ideal){
        Student student = new Student(studentEntries[0], studentEntries[1]);
        for (String category: categories.keySet()){
            if(!category.equals("\uFEFF")) {
                if(!category.equals("Преподавателю о курсе"))
                    student.modules.add(createModule(category, studentEntries, categories, ideal));
            }
        }
        return student;
    }

    public static HashMap map(String[] header, String[] values){
        HashMap<String, String> map = new LinkedHashMap<String, String>();
        for(int i = 0; i < header.length; i++){
            map.put(header[i], values[i]);
        }
        return map;
    }

    public HashMap<String, ArrayList<recordID>> mapList(String[] header, String[] values){
        HashMap<String, ArrayList<recordID>> map = new LinkedHashMap<String, ArrayList<recordID>>();
        for(int i = 0; i < header.length; i++){
            if(!map.containsKey(header[i])){
                map.put(header[i], new ArrayList<>());
                var list = map.get(header[i]);
                list.add(new recordID(i,values[i]));
            }
            else{
                var list = map.get(header[i]);
                list.add(new recordID(i,values[i]));
            }
        }
        return map;
    }

    public class recordID{
        final int id;
        final String info;
        recordID(int id, String info){
            this.id = id;
            this.info = info;
        }

        @Override
        public String toString() {
            return String.format("id: %s info: %s",id,info);
        }
    }
}
