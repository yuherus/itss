package controller;

import model.Location;
import model.Tracking;
import util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TrackingController implements CRUDController<Tracking> {
    private int getCurrentUserId() {
        // Giả sử phương thức này trả về ID của người dùng đang đăng nhập
        // Bạn cần thay thế phương thức này bằng cách lấy thông tin người dùng từ session hoặc context hiện tại
        return 1;
    }
    @Override
    public List<Tracking> getAll() throws SQLException {
        List<Tracking> trackings = new ArrayList<>();
        String query = "SELECT * FROM Tours " +
                "INNER JOIN Tour_Points ON Tours.tour_id = Tour_Points.tour_id " +
                "INNER JOIN Users ON Tours.tourist_id = Users.user_id " +
                "WHERE status = 'confirmed' AND Users.user_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, getCurrentUserId()); // Sử dụng PreparedStatement để thiết lập giá trị tham số

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Tracking tracking = new Tracking();
                tracking.setPointId(rs.getInt("point_id"));
                tracking.setTourId(rs.getInt("tour_id"));
                tracking.setVisitTime(rs.getTimestamp("visit_time").toLocalDateTime());

                // Giả sử rằng bạn đã có LocationController và Tracking được định nghĩa
                LocationController locationController = new LocationController();
                Location location = locationController.getById(rs.getInt("location_id"));
                tracking.setLocation(location);

                trackings.add(tracking);
            }
        }
        return trackings;
    }


    @Override
    public Tracking getById(int id) throws SQLException {
        Tracking tracking = null;
        String query = "SELECT * FROM Tour_Points WHERE id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tracking = new Tracking();
                tracking.setVisitTime(rs.getTimestamp("visit_time").toLocalDateTime());

                LocationController locationController = new LocationController();
                Location location = locationController.getById(rs.getInt("location_id"));
                tracking.setLocation(location);
            }
        }
        return tracking;
    }

    @Override
    public void add(Tracking tracking) throws SQLException {
        String query = "INSERT INTO Tour_Points (tour_id, location_id, visit_order, start_time, end_time) " +
                "VALUES (?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, tracking.getTourId());
            pstmt.setInt(2, tracking.getLocation().getLocationId());

            pstmt.setTimestamp(3, java.sql.Timestamp.valueOf(tracking.getVisitTime()));
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Tracking tracking) throws SQLException {
        String query = "UPDATE Tour_Points SET location_id = ?, visit_time = ? " +
                "WHERE tour_id = ? AND point_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, tracking.getLocation().getLocationId());
            pstmt.setTimestamp(2, java.sql.Timestamp.valueOf(tracking.getVisitTime()));
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {

    }

    public String getTourName() throws SQLException{
        String query = "SELECT * FROM Tours WHERE status = 'confirmed' AND Tours.tourist_id = ?" ;
        String tourName = null;
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, getCurrentUserId());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tourName = rs.getString("tour_name");
            }
        }

        return tourName;
    }

}
