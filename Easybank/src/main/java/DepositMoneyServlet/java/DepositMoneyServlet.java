package DepositMoneyServlet.java;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DatabaseConnection.java.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/DepositMoneyServlet")
public class DepositMoneyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String accountNumber = (String) session.getAttribute("accountNumber");
        double amountToDeposit = Double.parseDouble(request.getParameter("amount"));

        try (Connection connection = DatabaseConnection.initializeDatabase()) {
            // Update balance
            String updateQuery = "UPDATE customers SET initialBalance = initialBalance + ? WHERE accountNumber = ?";
            try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                updateStmt.setDouble(1, amountToDeposit);
                updateStmt.setString(2, accountNumber);
                updateStmt.executeUpdate();
            }

            // Record transaction
            String transactionQuery = "INSERT INTO transactions (account_number, transaction_type, transaction_amount, transaction_date) VALUES (?, 'deposit', ?, NOW())";
            try (PreparedStatement transactionStmt = connection.prepareStatement(transactionQuery)) {
                transactionStmt.setString(1, accountNumber);
                transactionStmt.setDouble(2, amountToDeposit);
                transactionStmt.executeUpdate();
            }

            response.sendRedirect("depositSuccess.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error n ");
        }
    }
}
