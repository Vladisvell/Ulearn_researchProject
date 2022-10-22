import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //Path path = Path.of("C:\\Users\\Vladislav\\IdeaProjects\\Ulearn_researchProject\\src\\basicprogramming_2.csv");
        System.out.println("Введите полное имя файла или локальное имя");
        Path path = Path.of(new Scanner(System.in).nextLine());
        List<String[]> data = Files.readAllLines(path, StandardCharsets.UTF_8).stream().map(line -> line.split(";", -1)).toList();
        String[] categoryHeader = data.get(0);
        String[] courseHeader = data.get(1);
        String[] idealHeader = data.get(2);
        idealHeader[0] = "Идеальное прохождение";
        List<String[]> personsData = data.subList(3, data.size());
        String token = "";
        for(int i = 0; i < categoryHeader.length; i++){
            if(!categoryHeader[i].equals(""))
                token = categoryHeader[i];
            else
                categoryHeader[i] = token;
        }
        List<HashMap> maps = personsData.stream().map(person -> CSVUtils.map(courseHeader, person)).toList();
        HashMap studentData = maps.get(6);
        System.out.println(studentData);
        HashMap<String, ArrayList<CSVUtils.recordID>> categories = new CSVUtils().mapList(categoryHeader, courseHeader);
        Student exampleStudent = CSVUtils.createStudent(personsData.get(4), categories, idealHeader);
        System.out.println(exampleStudent.getFullInfo());
    }
}