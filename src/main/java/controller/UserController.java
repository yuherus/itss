package controller;

import model.User;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static model.User.UserType.TOURIST;

public class UserController implements CRUDController<User> {
    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";

        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                switch (rs.getString("user_type")) {
                    case "admin":
                        user.setUserType(User.UserType.ADMIN);
                        break;
                    case "tour_guide":
                        user.setUserType(User.UserType.TOUR_GUIDE);
                        break;
                    case "tourist":
                        user.setUserType(TOURIST);
                        break;
                }
                user.setCreatedAt(rs.getTimestamp("created_at"));
                users.add(user);
            }
        }

        return users;
    }

    @Override
    public User getById(int userId) throws SQLException {
        User user = new User();
        String query = "SELECT * FROM Users WHERE user_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    switch (rs.getString("user_type")) {
                        case "admin":
                            user.setUserType(User.UserType.ADMIN);
                            break;
                        case "tour_guide":
                            user.setUserType(User.UserType.TOUR_GUIDE);
                            break;
                        case "tourist":
                            user.setUserType(TOURIST);
                            break;
                    }
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                }
            }
        }

        return user;
    }

    @Override
    public void add(User user) throws SQLException {
        String query = "INSERT INTO Users (username, password, name, email, user_type) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getUserType().toString().toLowerCase());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(User user) throws SQLException {
        String query = "UPDATE Users SET username = ?, password = ?, name = ?, email = ?, user_type = ? WHERE user_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getUserType().toString().toLowerCase());
            pstmt.setInt(6, user.getUserId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int userId) throws SQLException {
        String query = "DELETE FROM Users WHERE user_id = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        }
    }

    public static User login(String username, String password) throws SQLException {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";

        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    switch (rs.getString("user_type")) {
                        case "admin":
                            user.setUserType(User.UserType.ADMIN);
                            break;
                        case "tour_guide":
                            user.setUserType(User.UserType.TOUR_GUIDE);
                            break;
                        case "tourist":
                            user.setUserType(TOURIST);
                            break;
                    }
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    return user;
                }
            }
        }

        return null;
    }

    public static User signup(String username, String password, String name, String email) throws SQLException {
        String checkUserQuery = "SELECT COUNT(*) FROM Users WHERE username = ?";
        String insertUserQuery = "INSERT INTO Users (username, password, name, email, user_type) VALUES (?, ?, ?, ?, 'TOURIST')";

        try (Connection conn = JDBCUtil.getConnection()) {
            // Check if username already exists
            try (PreparedStatement checkStmt = conn.prepareStatement(checkUserQuery)) {
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    throw new SQLException("Username already exists");
                }
            }

            // Insert new user
            try (PreparedStatement insertStmt = conn.prepareStatement(insertUserQuery)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, password);
                insertStmt.setString(3, name);
                insertStmt.setString(4, email);
                insertStmt.executeUpdate();
            }
        }

        return new User(username, password, name, email, TOURIST);
    }

}
