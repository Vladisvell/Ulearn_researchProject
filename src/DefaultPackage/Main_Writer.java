package DefaultPackage;

import Vkontakte.VkApiRequests;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Main_Writer {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InterruptedException {
        conn.Conn();
        conn.WriteDB();
        System.out.println("База данных успешно заполнена.");
    }

    public static List<Student> GetFullData(List<Student> students) throws InterruptedException {
        VkApiRequests client = new VkApiRequests("C:\\Users\\Vladislav\\Desktop\\AccessCredentials.txt");
        System.out.println("Получаем данные из ВК!");
        var fulldata = client.requestGenders(students);
        System.out.println("Данные из ВК получены!");
        return fulldata;
    }
}