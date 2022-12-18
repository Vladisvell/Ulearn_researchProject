package Main;

import DefaultPackage.Student;
import DefaultPackage.conn;
import Vkontakte.VkApiRequests;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.*;

public class Main_Writer {

    //Данный модуль заполняет базу данных.
    final public static String AccessCredentialsPath = "C:\\Users\\Vladislav\\Desktop\\AccessCredentials.txt";
    final public static String CSVPath = "C:\\Users\\Vladislav\\Desktop\\basicprogramming_shadrinEdit.csv";
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException, InterruptedException {
        conn.Conn();
        boolean isVKNeeded = false; //Определяет, тягать ли данные из VKoнтакте
        conn.WriteDB(isVKNeeded);
        System.out.println("База данных успешно заполнена.");
    }

    public static List<Student> GetFullData(List<Student> students) throws InterruptedException {
        VkApiRequests client = new VkApiRequests(AccessCredentialsPath);
        System.out.println("Получаем данные из ВК!");
        var fulldata = client.requestGenders(students);
        System.out.println("Данные из ВК получены!");
        return fulldata;
    }
}