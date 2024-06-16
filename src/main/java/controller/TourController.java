package controller;

import javafx.util.Pair;
import model.Location;
import model.SampleTour;
import model.Tour;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TourController implements CRUDController<Tour> {
    @Override
    public List<Tour> getAll() throws SQLException {
        List<Tour> tours = new ArrayList<>();
        String query = "SELECT * FROM Tours";

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Tour tour = new Tour();
                tour.setTourId(rs.getInt("tour_id"));
                tour.setTouristId(rs.getInt("tourist_id"));
                tour.setTourGuideId(rs.getInt("tour_guide_id"));
                tour.setTourName(rs.getString("tour_name"));
                switch (rs.getString("status")) {
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
                tour.setStartDate(rs.getDate("start_date"));
                tour.setEndDate(rs.getDate("end_date"));
                tour.setTotalCost(rs.getDouble("total_cost"));
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
                tours.add(tour);
            }
        }
        return tours;
    }

    @Override
    public Tour getById(int id) throws SQLException {
        Tour tour = new Tour();
        String query = "SELECT * FROM Tours WHERE tour_id = " + id;

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                tour.setTourId(rs.getInt("tour_id"));
                tour.setTouristId(rs.getInt("tourist_id"));
                tour.setTourGuideId(rs.getInt("tour_guide_id"));
                tour.setTourName(rs.getString("tour_name"));
                switch (rs.getString("status")) {
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
                tour.setStartDate(rs.getDate("start_date"));
                tour.setEndDate(rs.getDate("end_date"));
                tour.setTotalCost(rs.getDouble("total_cost"));
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
        return tour;
    }

    @Override
    public void add(Tour tour) throws SQLException {
        String query = "INSERT INTO Tours (tourist_id, tour_guide_id, tour_name, description, status, start_date, end_date, total_cost) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, tour.getTouristId());
            pstmt.setInt(2, tour.getTourGuideId());
            pstmt.setString(3, tour.getTourName());
            pstmt.setString(4, tour.getDescription());
            pstmt.setString(5, tour.getStatus().toString().toLowerCase());
            pstmt.setDate(6, tour.getStartDate());
            pstmt.setDate(7, tour.getEndDate());
            pstmt.setDouble(8, tour.getTotalCost());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    tour.setTourId(rs.getInt(1));
                }
            }

            for (Pair<Location, Timestamp> location : tour.getLocations()) {
                String addLocationQuery = "INSERT INTO Tour_Points (tour_id, location_id, visit_time) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt2 = conn.prepareStatement(addLocationQuery)) {
                    pstmt2.setInt(1, tour.getTourId());
                    pstmt2.setInt(2, location.getKey().getLocationId());
                    pstmt2.setTimestamp(3, location.getValue());
                    pstmt2.executeUpdate();
                }
            }
        }
    }

    @Override
    public void update(Tour tour) throws SQLException {
        String query = "UPDATE Tours SET tour_guide_id = ?, tour_name = ?, status = ?, start_date = ?, end_date = ?, total_cost = ? WHERE tour_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, tour.getTourGuideId());
            pstmt.setString(2, tour.getTourName());
            pstmt.setString(3, tour.getStatus().toString());
            pstmt.setDate(4, tour.getStartDate());
            pstmt.setDate(5, tour.getEndDate());
            pstmt.setDouble(6, tour.getTotalCost());
            pstmt.setInt(7, tour.getTourId());
            pstmt.executeUpdate();

            String deleteLocationsQuery = "DELETE FROM Tour_Points WHERE tour_id = ?";
            try (PreparedStatement pstmt2 = conn.prepareStatement(deleteLocationsQuery)) {
                pstmt2.setInt(1, tour.getTourId());
                pstmt2.executeUpdate();
            }

            for (Pair<Location, Timestamp> location : tour.getLocations()) {
                String addLocationQuery = "INSERT INTO Tour_Points (tour_id, location_id, visit_time) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt3 = conn.prepareStatement(addLocationQuery)) {
                    pstmt3.setInt(1, tour.getTourId());
                    pstmt3.setInt(2, location.getKey().getLocationId());
                    pstmt3.setTimestamp(3, location.getValue());
                    pstmt3.executeUpdate();
                }
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Tours WHERE tour_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Tour> getByUserId() throws SQLException{
        List<Tour> tours = new ArrayList<>();
        String query = "SELECT * FROM Tours INNER JOIN Users ON Tours.tourist_id = Users.user_id WHERE Users.user_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, Integer.parseInt(System.getProperty("userId")));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Tour tour = new Tour();
                tour.setTourId(rs.getInt("tour_id"));
                tour.setTouristId(rs.getInt("tourist_id"));
                tour.setTourGuideId(rs.getInt("tour_guide_id"));
                tour.setTourName(rs.getString("tour_name"));
                switch (rs.getString("status")) {
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
                tour.setStartDate(rs.getDate("start_date"));
                tour.setEndDate(rs.getDate("end_date"));
                tour.setTotalCost(rs.getDouble("total_cost"));
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
                tours.add(tour);
            }
        }
        return tours;
    }
}
