package controller;

import javafx.util.Pair;
import model.Location;
import model.Notification;
import model.Tour;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationController implements CRUDController<Notification> {

    @Override
    public List<Notification> getAll() throws SQLException {
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM Notifications";

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationId(rs.getInt("notification_id"));
                notification.setUser(new UserController().getById(rs.getInt("user_id")));
                notification.setMessage(rs.getString("message"));
                notification.setStatus(rs.getString("status").equals("unread") ? false : true);
                notification.setTour(new TourController().getById(rs.getInt("tour_id")));
                notification.setTourType(rs.getString("tour_type").equals("optional") ? Notification.TourType.OPTIONALBOOK : Notification.TourType.QUICKBOOK);
                notifications.add(notification);
            }
        }

        return notifications;
    }

    @Override
    public Notification getById(int id) throws SQLException {
        Notification notification = new Notification();
        String query = "SELECT * FROM Notifications WHERE notification_id = " + id;

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                notification.setNotificationId(rs.getInt("notification_id"));
                notification.setUser(new UserController().getById(rs.getInt("user_id")));
                notification.setMessage(rs.getString("message"));
                notification.setStatus(rs.getString("status").equals("unread") ? false : true);
                notification.setTour(new TourController().getById(rs.getInt("tour_id")));
                notification.setTourType(rs.getString("tour_type").equals("optional") ? Notification.TourType.OPTIONALBOOK : Notification.TourType.QUICKBOOK);
            }
        }

        return notification;
    }

    @Override
    public void add(Notification notification) throws SQLException {
        String query = "INSERT INTO Notifications (user_id, message, status, tour_id, tour_type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, notification.getUser().getUserId());
            pstmt.setString(2, notification.getMessage());
            pstmt.setString(3, notification.getStatus() ? "read" : "unread");
            pstmt.setInt(4, notification.getTour().getTourId());
            pstmt.setString(5, notification.getTourType() == Notification.TourType.OPTIONALBOOK ? "optional" : "quick");
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Notification notification) throws SQLException {
        String query = "UPDATE Notifications SET user_id = ?, message = ?, status = ?, tour_id = ?, tour_type = ? WHERE notification_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, notification.getUser().getUserId());
            pstmt.setString(2, notification.getMessage());
            pstmt.setString(3, notification.getStatus() ? "read" : "unread");
            pstmt.setInt(4, notification.getTour().getTourId());
            pstmt.setString(5, notification.getTourType() == Notification.TourType.OPTIONALBOOK ? "optional" : "quick");
            pstmt.setInt(6, notification.getNotificationId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Notifications WHERE notification_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Notification> getByUserId(int id) throws SQLException{
        List<Notification> notifications = new ArrayList<>();
        String query = "SELECT * FROM Notifications INNER JOIN Users ON Notifications.user_id = Users.user_id WHERE Users.user_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Notification notification = new Notification();
                notification.setNotificationId(rs.getInt("notification_id"));
                notification.setUser(new UserController().getById(rs.getInt("user_id")));
                notification.setMessage(rs.getString("message"));
                notification.setStatus(rs.getString("status").equals("unread") ? false : true);
                notification.setTour(new TourController().getById(rs.getInt("tour_id")));
                notification.setTourType(rs.getString("tour_type").equals("optional") ? Notification.TourType.OPTIONALBOOK : Notification.TourType.QUICKBOOK);
                notifications.add(notification);
            }
        }
        return notifications;
    }
}
