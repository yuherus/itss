package controller;

import model.Location;
import model.User;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocationController implements CRUDController<Location>{

    @Override
    public List<Location> getAll() throws SQLException {
        List<Location> locations = new ArrayList<>();
        String query = "SELECT * FROM Locations";

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Location location = new Location();
                location.setLocationId(rs.getInt("location_id"));
                location.setName(rs.getString("name"));
                location.setName(rs.getString("description"));
                location.setAddress(rs.getString("address"));
                StyleController styleController = new StyleController();
                location.setStyle(styleController.getById(rs.getInt("style_id")));
                location.setImageUrl(rs.getString("imgUrl"));
                locations.add(location);
            }
        }

        return locations;
    }

    @Override
    public Location getById(int id) throws SQLException {
        Location location = new Location();
        String query = "SELECT * FROM Locations WHERE location_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        location.setLocationId(rs.getInt("location_id"));
                        location.setName(rs.getString("name"));
                        location.setName(rs.getString("description"));
                        location.setAddress(rs.getString("address"));
                        StyleController styleController = new StyleController();
                        location.setStyle(styleController.getById(rs.getInt("style_id")));
                        location.setImageUrl(rs.getString("imgUrl"));
                }} catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        }

        return location;
    }

    @Override
    public void add(Location location) throws SQLException {
        String query = "INSERT INTO Locations (name, description, address, style_id, imgUrl) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             pstmt.setString(1, location.getName());
             pstmt.setString(2, location.getDescription());
             pstmt.setString(3, location.getAddress());
             pstmt.setInt(4, location.getStyle().getStyleId());
             pstmt.setString(5, location.getImageUrl());
             pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Location location) throws SQLException {
        String query = "UPDATE Locations SET name = ?, description = ?, address = ?, style_id = ?, imgUrl = ? WHERE location_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             pstmt.setString(1, location.getName());
             pstmt.setString(2, location.getDescription());
             pstmt.setString(3, location.getAddress());
             pstmt.setInt(4, location.getStyle().getStyleId());
             pstmt.setString(5, location.getImageUrl());
             pstmt.setInt(6, location.getLocationId());
             pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Locations WHERE location_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
