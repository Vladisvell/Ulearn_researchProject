package CSVUtilities;

import DefaultPackage.Student;
import DefaultPackage.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class CSVUtils {
    public static List<Student> read(){
        //System.out.println("Введите полное имя файла или локальное имя");
        //Path path = Path.of(new Scanner(System.in).nextLine());
        Path path = Path.of("C:\\Users\\Vladislav\\Desktop\\basicprogramming_shadrinEdit.csv");
        //Path path = Path.of("C:\\Users\\Vladislav\\Desktop\\basicprogramming_2.csv");
        List<String[]> data = new ArrayList<>();
        try {
            //data = Files.readAllLines(path, StandardCharsets.UTF_8).stream().map(line -> line.split(";", -1)).toList();
            data = Files.readAllLines(path, StandardCharsets.UTF_8).stream().map(line -> line.split(";", -1)).toList();
            //var esse = Files.readAllLines(path, StandardCharsets.UTF_8);
            System.out.println();
        } catch (IOException e) {
            throw new RuntimeException(e.fillInStackTrace());
        }
        String[] categoryHeader = data.get(0);
        String[] courseHeader = data.get(1);
        String[] idealHeader = data.get(2);
        idealHeader[0] = "Идеальное прохождение";
        List<String[]> personsData = data.subList(2, data.size());
        String token = "";
        for(int i = 0; i < categoryHeader.length; i++){
            if(!categoryHeader[i].equals(""))
                token = categoryHeader[i];
            else
                categoryHeader[i] = token;
        }
        //List<HashMap> maps = personsData.stream().map(person -> CSVUtilities.CSVUtils.map(courseHeader, person)).toList();
        //HashMap studentData = maps.get(6);
        HashMap<String, ArrayList<CSVUtils.recordID>> categories = new CSVUtils().mapList(categoryHeader, courseHeader);
        //DefaultPackage.Student exampleStudent = DefaultPackage.Student.createStudent(personsData.get(4), categories, idealHeader);
        return personsData
                .stream()
                .map(student -> Student.createStudent(student, categories, idealHeader)).toList();
    }

    private static HashMap map(String[] header, String[] values){
        HashMap<String, String> map = new LinkedHashMap<String, String>();
        for(int i = 0; i < header.length; i++){
            map.put(header[i], values[i]);
        }
        return map;
    }

    private HashMap<String, ArrayList<recordID>> mapList(String[] header, String[] values){
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

        public int getId() {
            return id;
        }

        public String getInfo() {
            return info;
        }
    }

    public static void main(String[] args){
        var students = CSVUtils.read();
    }
}
