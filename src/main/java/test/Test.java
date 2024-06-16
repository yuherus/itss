package test;

import controller.SampleTourController;
import controller.StyleController;
import controller.TourController;
import model.SampleTour;
import model.Style;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
//        test jdbc
        try {
            StyleController styleController = new StyleController();
            Style styles = styleController.getById(2);
            System.out.println(styles.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
