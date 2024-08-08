package WithdrawMoneyServlet.java;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DatabaseConnection.java.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/WithdrawMoneyServlet")
public class WithdrawMoneyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String accountNumber = (String) session.getAttribute("accountNumber");
        double amountToWithdraw = Double.parseDouble(request.getParameter("amount"));

        try (Connection connection = DatabaseConnection.initializeDatabase()) {
            // Check current balance
            String balanceQuery = "SELECT initialBalance FROM customers WHERE accountNumber = ?";
            try (PreparedStatement balanceStmt = connection.prepareStatement(balanceQuery)) {
                balanceStmt.setString(1, accountNumber);
                ResultSet rs = balanceStmt.executeQuery();
                if (rs.next()) {
                    double currentBalance = rs.getDouble("initialBalance");
                    if (currentBalance >= amountToWithdraw) {
                        // Deduct amount
                        String updateQuery = "UPDATE customers SET initialBalance = initialBalance - ? WHERE accountNumber = ?";
                        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                            updateStmt.setDouble(1, amountToWithdraw);
                            updateStmt.setString(2, accountNumber);
                            updateStmt.executeUpdate();
                        }

                        // Record transaction
                        String transactionQuery = "INSERT INTO transactions (account_number, transaction_type, transaction_amount, transaction_date) VALUES (?, 'withdraw', ?, NOW())";
                        try (PreparedStatement transactionStmt = connection.prepareStatement(transactionQuery)) {
                            transactionStmt.setString(1, accountNumber);
                            transactionStmt.setDouble(2, amountToWithdraw);
                            transactionStmt.executeUpdate();
                        }

                        response.sendRedirect("withdrawSuccess.jsp");
                    } else {
                        request.setAttribute("errorMessage", "Insufficient balance");
                        request.getRequestDispatcher("withdrawMoney.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("errorMessage", "Account not found");
                    request.getRequestDispatcher("withdrawMoney.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println(e.getMessage());
        }
    }
}
