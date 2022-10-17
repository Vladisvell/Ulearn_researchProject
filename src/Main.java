import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //File csvData = new File("C:\\Users\\Vladislav\\IdeaProjects\\Ulearn_researchProject\\src\\basicprogramming_2.csv");
        //CSVParser parser = CSVParser.parse(csvData, StandardCharsets.UTF_8, CSVFormat.EXCEL);
        Path path = Path.of("C:\\Users\\Vladislav\\IdeaProjects\\Ulearn_researchProject\\src\\basicprogramming_2.csv");
        List<String[]> data = Files.readAllLines(path, StandardCharsets.UTF_8).stream().map(line -> line.split(";")).toList();
        String[] firstHeader = data.get(0);
        String[] secondHeader = data.get(1);
        String[] thirdHeader = data.get(2);
        thirdHeader[0] = "Идеальное прохождение";
        List<String[]> personsData = data.subList(3, data.size());
        String token = "";
        for(int i = 0; i < firstHeader.length; i++){
            if(!firstHeader[i].equals(""))
                token = firstHeader[i];
            else
                firstHeader[i] = token;
        } //заполнили пустые поля, чтобы было дорого-богато и вообще корректно поля относились к нужным категориям. Да.

        List<HashMap> maps = personsData.stream().map(person -> map(secondHeader, person)).toList();
        HashMap ideal = map(secondHeader, thirdHeader); //содержит в себе максимумы за прохождения разделов, отсюда можно извлечь максимумы

        HashMap studentData = maps.get(0);
        Student exampleStudent = new Student((String) studentData.get("Фамилия Имя"), (String) studentData.get("Группа"));
        System.out.println(exampleStudent.toString());
        //здесь мы сделал
    }

    private static HashMap map(String[] header, String[] values){
        HashMap<String, String> map = new LinkedHashMap<String, String>();
        for(int i = 0; i < header.length; i++){
            map.put(header[i], values[i]);
        }
        return map;
    }

}