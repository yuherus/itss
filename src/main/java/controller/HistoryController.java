package controller;

import javafx.util.Pair;
import model.Location;
import model.Tour;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HistoryController implements CRUDController<Tour> {

    private int getCurrentUserId() {
        // Giả sử phương thức này trả về ID của người dùng đang đăng nhập
        // Bạn cần thay thế phương thức này bằng cách lấy thông tin người dùng từ session hoặc context hiện tại
        return 1;
    }

    @Override
    public List<Tour> getAll() throws SQLException {
        List<Tour> tours = new ArrayList<>();
        int userId = getCurrentUserId();
        String query = "SELECT * FROM Tours WHERE tourist_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Tour tour = new Tour();
                tour.setTourId(rs.getInt("tour_id"));

                String getTourDetailsQuery = "SELECT * FROM Tours WHERE tour_id = ?";
                try (PreparedStatement pstmt2 = conn.prepareStatement(getTourDetailsQuery)) {
                    pstmt2.setInt(1, rs.getInt("tour_id"));
                    try (ResultSet tourDetailsRs = pstmt2.executeQuery()) {
                        if (tourDetailsRs.next()) {
                            tour.setTourGuideId(tourDetailsRs.getInt("tour_guide_id"));
                            tour.setTourName(tourDetailsRs.getString("tour_name"));
                            tour.setStartDate(tourDetailsRs.getDate("start_date"));
                            tour.setEndDate(tourDetailsRs.getDate("end_date"));
                            tour.setTotalCost(tourDetailsRs.getDouble("total_cost"));
                            switch (tourDetailsRs.getString("status")) {
                                case "pending":
                                    tour.setStatus(Tour.Status.PENDING);
                                    break;
                                case "confirmed":
                                    tour.setStatus(Tour.Status.CONFIRMED);
                                    break;
                                case "completed":
                                    tour.setStatus(Tour.Status.COMPLETED);
                                    break;
                                case "cancelled":
                                    tour.setStatus(Tour.Status.CANCELLED);
                                    break;
                            }
                            String getLocationsQuery = "SELECT * FROM Tour_Points WHERE tour_id = " + rs.getInt("tour_id");
                            try (Statement stmt2 = conn.createStatement();
                                 ResultSet rs2 = stmt2.executeQuery(getLocationsQuery)) {
                                List<Pair<Location, Timestamp>> locations = new ArrayList<>();
                                while (rs2.next()) {
                                    LocationController locationController = new LocationController();
                                    Location location = locationController.getById(rs2.getInt("location_id"));
                                    locations.add(new Pair<>(location, rs2.getTimestamp("visit_time")));
                                }
                                tour.setLocations(locations);
                            }

                        }
                    }
                }

                tours.add(tour);
            }
        }
        tours.sort(Comparator.comparing(Tour::getStartDate).reversed());
        return tours;
    }

    @Override
    public Tour getById(int id) throws SQLException {
        Tour tour = null;
        int userId = getCurrentUserId();
        String query = "SELECT * FROM Tour_Tourists WHERE id = ? AND tourist_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.setInt(2, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tour = new Tour();
                tour.setTourId(rs.getInt("tour_id"));

                String getTourDetailsQuery = "SELECT * FROM Tours WHERE tour_id = ?";
                try (PreparedStatement pstmt2 = conn.prepareStatement(getTourDetailsQuery)) {
                    pstmt2.setInt(1, rs.getInt("tour_id"));
                    try (ResultSet tourDetailsRs = pstmt2.executeQuery()) {
                        if (tourDetailsRs.next()) {
                            tour.setTourGuideId(tourDetailsRs.getInt("tour_guide_id"));
                            tour.setTourName(tourDetailsRs.getString("tour_name"));
                            tour.setStartDate(tourDetailsRs.getDate("start_date"));
                            tour.setEndDate(tourDetailsRs.getDate("end_date"));
                            tour.setTotalCost(tourDetailsRs.getDouble("total_cost"));
                            switch (tourDetailsRs.getString("status")) {
                                case "pending":
                                    tour.setStatus(Tour.Status.PENDING);
                                    break;
                                case "confirmed":
                                    tour.setStatus(Tour.Status.CONFIRMED);
                                    break;
                                case "completed":
                                    tour.setStatus(Tour.Status.COMPLETED);
                                    break;
                                case "cancelled":
                                    tour.setStatus(Tour.Status.CANCELLED);
                                    break;
                            }
                            String getLocationsQuery = "SELECT * FROM Tour_Points WHERE tour_id = " + rs.getInt("tour_id");
                            try (Statement stmt2 = conn.createStatement();
                                 ResultSet rs2 = stmt2.executeQuery(getLocationsQuery)) {
                                List<Pair<Location, Timestamp>> locations = new ArrayList<>();
                                while (rs2.next()) {
                                    LocationController locationController = new LocationController();
                                    Location location = locationController.getById(rs2.getInt("location_id"));
                                    locations.add(new Pair<>(location, rs2.getTimestamp("visit_order")));
                                }
                                tour.setLocations(locations);
                            }

                        }
                    }
                }
            }
        }
        return tour;
    }

    @Override
    public void add(Tour tour) throws SQLException {
        int userId = getCurrentUserId();
        String query = "INSERT INTO Tour_Tourists (tour_id, tourist_id) VALUES (?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, tour.getTourId());
            pstmt.setInt(2, userId); // Use current user ID as tourist_id
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Tour tour) throws SQLException {
        int userId = getCurrentUserId();
        String query = "UPDATE Tour_Tourists SET tour_id = ?, tourist_id = ? WHERE id = ? AND tourist_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, tour.getTourId());
            pstmt.setInt(2, userId); // Use current user ID as tourist_id
            pstmt.setInt(3, tour.getTourId()); // Assuming id is passed through TourId for now
            pstmt.setInt(4, userId); // Ensure only current user's records are updated
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        int userId = getCurrentUserId();
        String query = "DELETE FROM Tour_Tourists WHERE id = ? AND tourist_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, userId); // Ensure only current user's records are deleted
            pstmt.executeUpdate();
        }
    }
}
