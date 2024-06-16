package controller;

import javafx.util.Pair;
import model.Location;
import model.SampleTour;
import model.User;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SampleTourController implements CRUDController<SampleTour>{

    @Override
    public List<SampleTour> getAll() throws SQLException {
        List<SampleTour> sampleTours = new ArrayList<>();
        String query = "SELECT * FROM Sample_Tour";

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                SampleTour sampleTour = new SampleTour();
                sampleTour.setSampleTourId(rs.getInt("sampletour_id"));
                sampleTour.setTourName(rs.getString("tour_name"));
                sampleTour.setDescription(rs.getString("description"));
                sampleTour.setTotalCost(rs.getDouble("total_cost"));
                String getLocationsQuery = "SELECT * FROM Sample_Tour_Points WHERE sampletour_id = " + rs.getInt("sampletour_id");
                try (Statement stmt2 = conn.createStatement();
                     ResultSet rs2 = stmt2.executeQuery(getLocationsQuery)) {
                    List<Pair<Location, Timestamp>> locations = new ArrayList<>();
                    while (rs2.next()) {
                        LocationController locationController = new LocationController();
                        Location location = locationController.getById(rs2.getInt("location_id"));
                        locations.add(new Pair<>(location, rs2.getTimestamp("visit_time")));
                    }
                    sampleTour.setLocations(locations);
                }
                sampleTours.add(sampleTour);
            }
        }
        return sampleTours;
    }

    @Override
    public SampleTour getById(int id) throws SQLException {
        SampleTour sampleTour = new SampleTour();
        String query = "SELECT * FROM Sample_Tour WHERE sampletour_id = " + id;

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                sampleTour.setSampleTourId(rs.getInt("sampletour_id"));
                sampleTour.setTourName(rs.getString("tour_name"));
                sampleTour.setDescription(rs.getString("description"));
                sampleTour.setTotalCost(rs.getDouble("total_cost"));
                String getLocationsQuery = "SELECT * FROM Sample_Tour_Points WHERE sampletour_id = " + rs.getInt("sampletour_id");
                try (Statement stmt2 = conn.createStatement();
                     ResultSet rs2 = stmt2.executeQuery(getLocationsQuery)) {
                    List<Pair<Location, Timestamp>> locations = new ArrayList<>();
                    while (rs2.next()) {
                        LocationController locationController = new LocationController();
                        Location location = locationController.getById(rs2.getInt("location_id"));
                        locations.add(new Pair<>(location, rs2.getTimestamp("visit_time")));
                    }
                    sampleTour.setLocations(locations);
                }
            }
        }
        return sampleTour;
    }

    @Override
    public void add(SampleTour sampleTour) throws SQLException {
        String query = "INSERT INTO Sample_Tour (tour_name, description, total_cost) VALUES (?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, sampleTour.getTourName());
            pstmt.setString(2, sampleTour.getDescription());
            pstmt.setDouble(3, sampleTour.getTotalCost());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(SampleTour sampleTour) throws SQLException {
        String query = "UPDATE Sample_Tour SET tour_name = ?, description = ?, total_cost = ? WHERE sampletour_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, sampleTour.getTourName());
            pstmt.setString(2, sampleTour.getDescription());
            pstmt.setDouble(3, sampleTour.getTotalCost());
            pstmt.setInt(4, sampleTour.getSampleTourId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Sample_Tour WHERE sampletour_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
