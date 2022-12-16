package DefaultPackage;

import CSVUtilities.CSVUtils;

import java.sql.*;
import java.util.*;


public class conn {
    private static Connection conn;
    private static Statement statmt;
    private static ResultSet    resSet;

    private static List<Student> studentList;
    private static List<String> groups;
    private static HashMap<String, Integer> groupsIDs;
    private static List<String> moduleNames;
    private static Student idealWalkthrough;

    public static int[] studentsStats;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:ulearn_statistics.db");
        statmt = conn.createStatement();

        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB() throws SQLException, InterruptedException {
        GetStatisticsData();
        WriteIdeal();
        WriteGroups();
        WriteSex();
        WritePeople();
        WriteUsers();
        WriteCourseNames();
        WriteAllCourses();
        System.out.println("Таблицы заполнены");
    }

    private static void WriteIdeal() throws SQLException {
        String rawTable = "CREATE TABLE if NOT EXISTS 'Информация о курсе'" +
                "('id' INTEGER PRIMARY KEY," +
                "'Практики' float,'ДЗ' float,'Активности' float);";
        var ech = idealWalkthrough.modules;
        PreparedStatement ptTable = conn.prepareStatement(rawTable);
        ptTable.executeUpdate();
        String rawStatement = "INSERT INTO 'Информация о курсе' ('id','Практики', 'ДЗ','Активности') VALUES (?,?,?,?)";
        PreparedStatement pt = conn.prepareStatement(rawStatement);
        var modules = idealWalkthrough.modules;
        for(int i = 1; i < modules.size()+1; i++){
            pt.setInt(1, i);
            pt.setFloat(2, modules.get(i-1).getMaxPracticePoints());
            pt.setFloat(3, modules.get(i-1).getMaxHomeworkPoints());
            pt.setFloat(4, modules.get(i-1).getMaxActivitiesPoints());
            pt.executeUpdate();
        }
    }

    private static void WriteCourseNames() throws SQLException{
        statmt.execute("CREATE TABLE if not exists 'Модули' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Название' text);");
        var names = idealWalkthrough.getModulesNames();
        String rawStatement = "INSERT INTO 'Модули' ('Название') VALUES (?)";
        PreparedStatement pt = conn.prepareStatement(rawStatement);
        var modules = idealWalkthrough.modules;
        for (int i = 1; i < modules.size()+1; i++) {
            pt.setString(1, modules.get(i - 1).getName());
            pt.executeUpdate();
        }
    }

    private static void GetStatisticsData() throws InterruptedException {
        studentList = CSVUtils.read();
        idealWalkthrough = studentList.get(0);
        studentList = studentList.subList(1,studentList.size()-1);
        studentList = Main_Writer.GetFullData(studentList);
        groups = StudentUtilities.GetAllGroups(studentList);
        groupsIDs = new HashMap<>();
        for(int i = 0; i < groups.size(); i++){
            groupsIDs.put(groups.get(i), i+1);
        }
    }

    private static void WritePeople() throws SQLException{
        statmt.execute("CREATE TABLE if not exists 'Люди' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Имя' text, 'Пол');");
        for(Student student: studentList){
            var studName = student.getName();
            var group = student.getGroup();
            var sex = student.getGender().ordinal();
            String raw = "INSERT INTO 'Люди' ('Имя', 'Пол') VALUES (?,?)";
            PreparedStatement pt = conn.prepareStatement(raw);
            pt.setString(1, studName);
            pt.setInt(2, sex);
            pt.executeUpdate();
        }
    }

    private static void WriteSex() throws  SQLException{
        statmt.execute("CREATE TABLE if not exists 'Полы' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Пол' text);");
        String raw = "INSERT INTO 'Полы' ('Пол') VALUES (?)";
        PreparedStatement pt = conn.prepareStatement(raw);
        pt.setString(1, "Неопределено");
        pt.executeUpdate();
        pt.setString(1, "Женский");
        pt.executeUpdate();
        pt.setString(1, "Мужской");
        pt.executeUpdate();
    }

    private static void WriteGroups() throws SQLException{
        statmt.execute("CREATE TABLE if not exists 'Группы' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Группа' text);");
        String raw = "INSERT INTO 'Группы' ('Группа') VALUES (?)";
        PreparedStatement pt = conn.prepareStatement(raw);
        if (groups == null)
            groups = StudentUtilities.GetAllGroups(studentList);
        for(String group: groups){
            pt.setString(1,group);
            pt.executeUpdate();
        }
    }
    private static void WriteUsers() throws SQLException{
        statmt.execute("CREATE TABLE if not exists 'Студенты' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'Группа' int);");
        for(Student student: studentList){
            var studName = student.getName();
            var group = student.getGroup();
            String raw = "INSERT INTO 'Студенты' ('Группа') VALUES (?)";
            PreparedStatement pt = conn.prepareStatement(raw);
            pt.setInt(1, groupsIDs.get(group));
            pt.executeUpdate();
        }

    }

    private static void WriteAllCourses() throws SQLException{
        if(studentList == null)
            studentList = CSVUtils.read();
        idealWalkthrough = studentList.get(0);
        studentList = studentList.subList(1,studentList.size()-1);
        moduleNames = studentList.get(0).getModulesNames();
        for(String moduleName : moduleNames){
            WriteModule(moduleName);
        }
    }

    private static void WriteModule(String moduleName) throws SQLException {
        if(studentList == null)
            studentList = CSVUtils.read();
        if(!studentList.get(0).doesModuleExist(moduleName))
            throw new IllegalArgumentException("Такого модуля не существует!");
        String rawTable = String.format("CREATE TABLE if NOT EXISTS '%s'" +
                "('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                "'Практики' float,'ДЗ' float,'Активности' float);",moduleName);
        PreparedStatement ptTable = conn.prepareStatement(rawTable);
        //ptTable.setString(1, moduleName);
        ptTable.executeUpdate();

        for(Student student: studentList){
            var module = student.findModuleByName(moduleName);
            var studName = student.getName();
            String raw = String.format("INSERT INTO '%s' ('Практики','ДЗ','Активности') VALUES (?,?,?)",moduleName);
            PreparedStatement pt = conn.prepareStatement(raw);
            //pt.setString(1,moduleName);
            pt.setFloat(1, module.getPracticePoints());
            pt.setFloat(2, module.getHomeworkPoints());
            pt.setFloat(3, module.getActivitiesPoints());
            pt.executeUpdate();
        }
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        //GetContainingFromInput();
        GetProgresses();
    }


    public static int[] GetProgresses() throws SQLException {
        if(studentsStats != null)
            return studentsStats;
        int[] stats = new int[]{0,0,0,0,0};
        resSet = statmt.executeQuery("SELECT * from 'Информация о курсе'");
        resSet.next();
        float maxPoints = resSet.getFloat("Практики") + resSet.getFloat("ДЗ");
        float onePercent = maxPoints / 100;
        resSet = statmt.executeQuery("Select * from 'За весь курс'");
        while(resSet.next()){
            float currPoints = resSet.getFloat("Практики") + resSet.getFloat("ДЗ");
            if(currPoints / onePercent < 20)
                stats[0]++;
            else if(currPoints / onePercent < 40)
                stats[1]++;
            else if(currPoints / onePercent < 60)
                stats[2]++;
            else if(currPoints / onePercent < 80)
                stats[3]++;
            else if(currPoints / onePercent <= 100)
                stats[4]++;
        }
        studentsStats = stats;
        return stats;
    }
    public static HashMap<String, Float> GetAverageScores() throws SQLException {
        resSet = statmt.executeQuery("SELECT * FROM 'Группы'");
        HashMap<Integer, Float> averageScoresInGroups = new HashMap<>();
        HashMap<Integer, Integer> studentsCountInGroups = new HashMap<>();
        HashMap<Integer, String> groups = new HashMap<>();
        while(resSet.next()){
            groups.put(resSet.getInt("id"), resSet.getString("Группа"));
            averageScoresInGroups.put(resSet.getInt("id"), 0f);
            studentsCountInGroups.put(resSet.getInt("id"), 0);
        }
        HashMap<Integer, Integer> studGroups = new HashMap<>();
        resSet = statmt.executeQuery("SELECT * FROM 'Студенты'");
        while(resSet.next()){
            studGroups.put(resSet.getInt("id"), resSet.getInt("Группа"));
        }
        resSet = statmt.executeQuery("SELECT * FROM 'За весь курс'");
        while(resSet.next()){
            Integer studId = resSet.getInt("id");
            float total = resSet.getFloat("Практики") + resSet.getFloat("ДЗ");
            Integer group = studGroups.get(studId);
            studentsCountInGroups.put(group, studentsCountInGroups.get(group)+1);
            averageScoresInGroups.put(group, averageScoresInGroups.get(group) + total);
        }
        HashMap<String, Float> averages = new HashMap<>();
        for(Integer groupID: averageScoresInGroups.keySet()){
            var count = studentsCountInGroups.get(groupID);
            var totalScore = averageScoresInGroups.get(groupID);
            averages.put(groups.get(groupID), totalScore / count);
        }
        return averages;
    }

    public static float[] GetAveragesFromModule(String moduleName) throws SQLException{
        String rawStatmt = String.format("SELECT * FROM '%s'", moduleName);
        resSet = statmt.executeQuery(rawStatmt);
        float[] stats = new float[]{0,0,0};
        int counter = 0;
        while (resSet.next()){
            counter++;
            stats[0] += resSet.getFloat("Практики");
            stats[1] += resSet.getFloat("ДЗ");
            stats[2] += resSet.getFloat("Активности");
        }
        stats[0] = stats[0] / counter;
        stats[1] = stats[1] / counter;
        stats[2] = stats[2] / counter;
        return stats;
    }

    public static LinkedHashMap<String, Float> GetAveragesFromAllModules() throws SQLException{
        resSet = statmt.executeQuery("Select * from Модули");
        List<String> names = new ArrayList<>();
        LinkedHashMap<String, Float> averagesFromAllModules = new LinkedHashMap<>();
        while(resSet.next()){
            names.add(resSet.getString("Название"));
        }
        var counter = 0;
        for(var name: names){
            String moduleName = name;
            float[] averages = GetAveragesFromModule(moduleName);
            float score = averages[0] + averages[1] + averages[2];
            averagesFromAllModules.put(moduleName, score);
        }
        return averagesFromAllModules;
    }

    public static LinkedHashMap<String, Float> GetIdealFromAllModules() throws SQLException{
        resSet = statmt.executeQuery("Select * from Модули");
        List<String> names = new ArrayList<>();
        LinkedHashMap<String, Float> averagesFromAllModules = new LinkedHashMap<>();
        while(resSet.next()){
            names.add(resSet.getString("Название"));
        }
        var counter = 0;
        resSet = statmt.executeQuery("Select * from 'Информация о курсе'");
        while(resSet.next()){
            String moduleName = names.get(counter);
            float score = resSet.getFloat("Практики") + resSet.getFloat("ДЗ");
            averagesFromAllModules.put(moduleName, score);
            counter++;
        }
        return averagesFromAllModules;
    }

    public static float[] GetIdealScores() throws SQLException{
        resSet = statmt.executeQuery("SELECT * FROM 'Информация о курсе'");
        float[] stats = new float[]{0,0,0};
        stats[0] = resSet.getFloat("Практики");
        stats[1] = resSet.getFloat("ДЗ");
        stats[2] = resSet.getFloat("Активности");
        return stats;
    }

    public static int[] GetSexes() throws SQLException{
        resSet = statmt.executeQuery("SELECT * FROM 'Люди'");
        int[] sexes = new int[]{0,0,0};
        while(resSet.next()){
            var sexID = resSet.getInt("Пол");
            sexes[sexID] += 1;
        }
        return sexes;
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