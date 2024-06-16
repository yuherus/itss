package controller;

import model.Location;
import model.Request;
import model.Style;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestController implements CRUDController<Request>{

    @Override
    public List<Request> getAll() throws SQLException {
        List<Request> requests = new ArrayList<>();
        String query = "SELECT * FROM Requests";

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Request request = new Request();
                request.setRequestId(rs.getInt("request_id"));
                request.setTouristId(rs.getInt("tourist_id"));
                request.setStyleId(rs.getInt("style_id"));
                List<Location> locations = new ArrayList<>();
                String getLocationsQuery = "SELECT * FROM Request_Locations WHERE request_id = " + rs.getInt("request_id");
                try (Statement stmt2 = conn.createStatement();
                     ResultSet rs2 = stmt2.executeQuery(getLocationsQuery)) {
                    while (rs2.next()) {
                        LocationController locationController = new LocationController();
                        Location location = locationController.getById(rs2.getInt("location_id"));
                        locations.add(location);
                    }
                    request.setLocations(locations);
                }
                requests.add(request);
            }
        }
        return requests;
    }

    @Override
    public Request getById(int id) throws SQLException {
        Request request = new Request();
        String query = "SELECT * FROM Requests WHERE request_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                request.setRequestId(rs.getInt("request_id"));
                request.setTouristId(rs.getInt("tourist_id"));
                request.setStyleId(rs.getInt("style_id"));
                List<Location> locations = new ArrayList<>();
                String getLocationsQuery = "SELECT * FROM Request_Locations WHERE request_id = " + rs.getInt("request_id");
                try (Statement stmt2 = conn.createStatement();
                     ResultSet rs2 = stmt2.executeQuery(getLocationsQuery)) {
                    while (rs2.next()) {
                        LocationController locationController = new LocationController();
                        Location location = locationController.getById(rs2.getInt("location_id"));
                        locations.add(location);
                    }
                    request.setLocations(locations);
                }
            }
        }
        return request;
    }

    @Override
    public void add(Request request) throws SQLException {
        String query = "INSERT INTO Requests (tourist_id, style_id) VALUES (?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, request.getTouristId());
            pstmt.setInt(2, request.getStyleId());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    request.setRequestId(rs.getInt(1));
                }
            }

            for (Location location : request.getLocations()) {
                String addLocationQuery = "INSERT INTO Request_Locations (request_id, location_id) VALUES (?, ?)";
                try (PreparedStatement pstmt2 = conn.prepareStatement(addLocationQuery)) {
                    pstmt2.setInt(1, request.getRequestId());
                    pstmt2.setInt(2, location.getLocationId());
                    pstmt2.executeUpdate();
                }
            }

        }


    }

    @Override
    public void update(Request request) throws SQLException {
        String query = "UPDATE Requests SET tourist_id = ?, style_id = ? WHERE request_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, request.getTouristId());
            pstmt.setInt(2, request.getStyleId());
            pstmt.setInt(3, request.getRequestId());
            pstmt.executeUpdate();

            String deleteLocationsQuery = "DELETE FROM Request_Locations WHERE request_id = ?";
            try (PreparedStatement pstmt2 = conn.prepareStatement(deleteLocationsQuery)) {
                pstmt2.setInt(1, request.getRequestId());
                pstmt2.executeUpdate();
            }

            for (Location location : request.getLocations()) {
                String addLocationQuery = "INSERT INTO Request_Locations (request_id, location_id) VALUES (?, ?)";
                try (PreparedStatement pstmt3 = conn.prepareStatement(addLocationQuery)) {
                    pstmt3.setInt(1, request.getRequestId());
                    pstmt3.setInt(2, location.getLocationId());
                    pstmt3.executeUpdate();
                }
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Requests WHERE request_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
