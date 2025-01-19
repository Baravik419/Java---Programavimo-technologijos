package courseWork.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBOperations {

    public static Connection connetToDb()
    {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jbdc.Driver");
            String DB_URL = "jdbc:mysql://localhost:3306/";
            String USER = "root";
            String PASS = "";

            con = DriverManager.getConnection(DB_URL, USER, PASS);
        }   catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return con;
    }

    public static void disconnectFromDb(Connection conn, Statement statement)
    {
        try {
            if (conn != null){
                conn.close();
            }
            if (statement != null){
                statement.close();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
