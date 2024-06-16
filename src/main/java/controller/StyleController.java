package controller;

import model.Style;
import model.User;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StyleController implements CRUDController<Style>{

    @Override
    public List<Style> getAll() throws SQLException {
        List<Style> styles = new ArrayList<>();
        String query = "SELECT * FROM Styles";

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Style style = new Style();
                style.setStyleId(rs.getInt("style_id"));
                style.setName(rs.getString("name"));
                style.setDescription(rs.getString("description"));
                styles.add(style);
            }
        }

        return styles;
    }

    @Override
    public Style getById(int id) throws SQLException {
        Style style = new Style();
        String query = "SELECT * FROM Styles WHERE style_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ptsmt = conn.prepareStatement(query)) {
            ptsmt.setInt(1, id);
            try (ResultSet rs = ptsmt.executeQuery()) {
                if (rs.next()) {
                    style.setStyleId(rs.getInt("style_id"));
                    style.setName(rs.getString("name"));
                    style.setDescription(rs.getString("description"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return style;
    }

    @Override
    public void add(Style style) throws SQLException {
        String query = "INSERT INTO Styles (name, description) VALUES (?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             pstmt.setString(1, style.getName());
             pstmt.setString(2, style.getDescription());
             pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Style style) throws SQLException {
        String query = "UPDATE Styles SET name = ?, description = ? WHERE style_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             pstmt.setString(1, style.getName());
             pstmt.setString(2, style.getDescription());
             pstmt.setInt(3, style.getStyleId());
             pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Styles WHERE style_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
             pstmt.setInt(1, id);
             pstmt.executeUpdate();
        }
    }
}
