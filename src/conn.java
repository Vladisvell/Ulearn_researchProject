import java.sql.*;


public class conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    public static boolean iscreated = true;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:exampleBase.s3db");

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'group' text);");

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB() throws SQLException
    {
        if(!iscreated){
            var students = CSVUtils.read();
            for(Student student: students){
                var studName = student.getName();
                var group = student.getGroup();
                String raw = "INSERT INTO 'users' ('name', 'group') VALUES (?,?)";
                PreparedStatement pt = conn.prepareStatement(raw);
                pt.setString(1, studName);
                pt.setString(2, group);
                pt.executeUpdate();
            }
            System.out.println("Таблица заполнена");
        }
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM users");

        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String  name = resSet.getString("name");
            String  group = resSet.getString("group");
            System.out.println( "ID = " + id );
            System.out.println( "name = " + name );
            System.out.println( "group = " + group );
            System.out.println();
        }

        //System.out.println("Таблица выведена");
    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
        statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }

}