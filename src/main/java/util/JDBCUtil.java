package util;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import com.mysql.cj.jdbc.Driver;

public class JDBCUtil {
    public static final String URL = "jdbc:mysql://localhost:3306/itss";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "001101";
    public static Connection getConnection() {
        Connection connection = null;
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
