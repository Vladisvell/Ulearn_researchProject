package DefaultPackage;

import DefaultPackage.conn;

import java.sql.SQLException;

public class DatabaseLauncher {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        conn.Conn();
        conn.CreateDB();
        //DefaultPackage.conn.WriteDB();
        conn.ReadDB();
        conn.CloseDB();
    }
}
