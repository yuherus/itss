package test;

import util.JDBCUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
//        test jdbc
        try {
            Connection connection = JDBCUtil.getConnection();
            Statement st = connection.createStatement();
            String sql = "SELECT * FROM Users";
            ResultSet result = st.executeQuery(sql);
            while (result.next()) {
                System.out.println(result.getString("username") + " " + result.getString("password"));
            }
            JDBCUtil.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
