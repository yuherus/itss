package controller;

import model.Payment;
import model.Style;
import util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentController implements CRUDController<Payment>{

    @Override
    public List<Payment> getAll() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String query = "SELECT * FROM Payments";
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentDate(rs.getTimestamp("payment_date"));
                switch (rs.getString("payment_method")) {
                    case "cash":
                        payment.setPaymentMethod(Payment.PaymentMethod.CASH);
                        break;
                    case "credit_card":
                        payment.setPaymentMethod(Payment.PaymentMethod.CREDIT_CARD);
                        break;
                    case "e_wallet":
                        payment.setPaymentMethod(Payment.PaymentMethod.E_WALLET);
                        break;
                }
                payment.setTour(new TourController().getById(rs.getInt("tour_id")));
                payment.setTourist(new UserController().getById(rs.getInt("user_id")));
                payments.add(payment);
            }
        }
        return payments;
    }

    @Override
    public Payment getById(int id) throws SQLException {
        Payment payment = new Payment();
        String query = "SELECT * FROM Payments WHERE payment_id = " + id;
        try (Connection conn = JDBCUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setPaymentDate(rs.getTimestamp("payment_date"));
                switch (rs.getString("payment_method")) {
                    case "cash":
                        payment.setPaymentMethod(Payment.PaymentMethod.CASH);
                        break;
                    case "credit_card":
                        payment.setPaymentMethod(Payment.PaymentMethod.CREDIT_CARD);
                        break;
                    case "e_wallet":
                        payment.setPaymentMethod(Payment.PaymentMethod.E_WALLET);
                        break;
                }
                payment.setTour(new TourController().getById(rs.getInt("tour_id")));
                payment.setTourist(new UserController().getById(rs.getInt("user_id")));
            }
        }
        return payment;
    }

    @Override
    public void add(Payment payment) throws SQLException {
        String query = "INSERT INTO Payments (tour_id, user_id, amount, payment_date, payment_method) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, payment.getTour().getTourId());
            pstmt.setInt(2, payment.getTourist().getUserId());
            pstmt.setDouble(3, payment.getAmount());
            pstmt.setTimestamp(4, payment.getPaymentDate());
            switch (payment.getPaymentMethod()) {
                case CASH:
                    pstmt.setString(5, "cash");
                    break;
                case CREDIT_CARD:
                    pstmt.setString(5, "credit_card");
                    break;
                case E_WALLET:
                    pstmt.setString(5, "e_wallet");
                    break;
            }
            pstmt.executeUpdate();
        }
    }

    @Override
    public void update(Payment payment) throws SQLException {
        String query = "UPDATE Payments SET tour_id = ?, user_id = ?, amount = ?, payment_date = ?, payment_method = ? WHERE payment_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, payment.getTour().getTourId());
            pstmt.setInt(2, payment.getTourist().getUserId());
            pstmt.setDouble(3, payment.getAmount());
            pstmt.setTimestamp(4, payment.getPaymentDate());
            switch (payment.getPaymentMethod()) {
                case CASH:
                    pstmt.setString(5, "cash");
                    break;
                case CREDIT_CARD:
                    pstmt.setString(5, "credit_card");
                    break;
                case E_WALLET:
                    pstmt.setString(5, "e_wallet");
                    break;
            }
            pstmt.setInt(6, payment.getPaymentId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM Payments WHERE payment_id = ?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}
