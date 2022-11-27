import java.sql.*;
import java.util.List;


public class conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    public static List<Student> studentList;

    public static boolean iscreated = false;
    public static boolean allcourseswritten = false;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:ulearn_statistics.db");

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        //statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'group' text);");
        //statmt.execute("CREATE TABLE if NOT EXISTS 'Allcourse'('id' INTEGER PRIMARY KEY AUTOINCREMENT,'name' text,'Practices' float,'Homeworks' float,'Activities' float);");

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB() throws SQLException {
        WriteUsers();
        WriteAllCources();
    }

    public static void WriteUsers() throws SQLException{
        statmt.execute("CREATE TABLE if not exists 'Пользователи' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Пользователь' text, 'Группа' text);");
        if(studentList == null)
            studentList = CSVUtils.read();
        for(Student student: studentList){
            var studName = student.getName();
            var group = student.getGroup();
            String raw = "INSERT INTO 'Пользователи' ('Пользователь', 'Группа') VALUES (?,?)";
            PreparedStatement pt = conn.prepareStatement(raw);
            pt.setString(1, studName);
            pt.setString(2, group);
            pt.executeUpdate();
        }
        System.out.println("Таблица заполнена");
    }

    public static void WriteAllCources() throws SQLException{
        if(studentList == null)
            studentList = CSVUtils.read();
        List<String> moduleNames = studentList.get(0).getModulesNames();
        for(String modulename : moduleNames){
            WriteModule(modulename);
        }
    }

    public static void WriteModule(String moduleName) throws SQLException {
        if(studentList == null)
            studentList = CSVUtils.read();
        if(!studentList.get(0).doesModuleExist(moduleName))
            throw new IllegalArgumentException("Такого модуля не существует!");
        String rawTable = String.format("CREATE TABLE if NOT EXISTS '%s'" +
                "('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'Пользователь' text,'Практики' float,'ДЗ' float,'Активности' float);",moduleName);
        PreparedStatement ptTable = conn.prepareStatement(rawTable);
        //ptTable.setString(1, moduleName);
        ptTable.executeUpdate();

        for(Student student: studentList){
            var module = student.findModuleByName(moduleName);
            var studName = student.getName();
            String raw = String.format("INSERT INTO '%s' ('Пользователь', 'Практики','ДЗ','Активности') VALUES (?,?,?,?)",moduleName);
            PreparedStatement pt = conn.prepareStatement(raw);
            //pt.setString(1,moduleName);
            pt.setString(1,studName);
            pt.setFloat(2, module.practicePoints);
            pt.setFloat(3, module.homeworkPoints);
            pt.setFloat(4, module.activitiesPoints);
            pt.executeUpdate();
        }
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        //resSet = statmt.executeQuery("SELECT * FROM Allcourse");

        /*while(resSet.next())
        {
            int id = resSet.getInt("id");
            String name = resSet.getString("name");
            float group = resSet.getFloat("Practices");
            System.out.println( "ID = " + id );
            System.out.println( "name = " + name );
            System.out.println( "Practices = " + group);
            System.out.println();
        }*/

        //System.out.println("Таблица выведена");
    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        //resSet.close();

        System.out.println("Соединения закрыты");
    }

}