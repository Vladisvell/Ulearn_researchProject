import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Main_Writer {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InterruptedException {
        //VkTestingField test = new VkTestingField();
        //test.run();
        VkApiRequests client = new VkApiRequests("C:\\Users\\Vladislav\\Desktop\\AccessCredentials.txt");
        List<Student> fulldata = null;
        //try {
        //    fulldata = client.requestGenders(CSVUtils.read());
        //} catch (InterruptedException e) {
        //    throw new RuntimeException(e);
        //}
        conn.Conn();
        conn.WriteDB();
        System.out.println("База данных успешно заполнена.");
    }

    public static List<Student> GetFullData(List<Student> students) throws InterruptedException {
        VkApiRequests client = new VkApiRequests("C:\\Users\\Vladislav\\Desktop\\AccessCredentials.txt");
        //var fulldata = client.requestGenders(students);
        var fulldata = students;
        return fulldata;
    }
}