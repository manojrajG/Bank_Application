package TransactionServlet.java;


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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/TransactionServlet")
public class TransactionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String accountNumber = (String) session.getAttribute("accountNumber");

        if (accountNumber == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        double amount = Double.parseDouble(request.getParameter("amount"));
        String message = "";
        boolean success = false;

        try (Connection connection = DatabaseConnection.initializeDatabase()) {
            if ("withdraw".equalsIgnoreCase(action)) {
                String query = "SELECT initialBalance FROM customers WHERE accountNumber = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, accountNumber);
                    ResultSet rs = preparedStatement.executeQuery();

                    if (rs.next()) {
                        double balance = rs.getDouble("initialBalance");
                        if (amount > 0 && amount <= balance) {
                            String updateQuery = "UPDATE customers SET initialBalance = initialBalance - ? WHERE accountNumber = ?";
                            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                                updateStatement.setDouble(1, amount);
                                updateStatement.setString(2, accountNumber);
                                updateStatement.executeUpdate();
                            }

                            // Insert transaction record
                            String insertTransactionQuery = "INSERT INTO transactions (account_number, transaction_type, transaction_amount, transaction_date) VALUES (?, ?, ?, ?)";
                            try (PreparedStatement insertStatement = connection.prepareStatement(insertTransactionQuery)) {
                                insertStatement.setString(1, accountNumber);
                                insertStatement.setString(2, "Withdraw");
                                insertStatement.setDouble(3, amount);
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                                insertStatement.setString(4, dateFormat.format(new Date()));
                                insertStatement.executeUpdate();
                            }

                            success = true;
                            message = "Withdrawal successful!";
                        } else {
                            message = "Insufficient balance or invalid amount.";
                        }
                    } else {
                        message = "Account not found.";
                    }
                }
            } else if ("deposit".equalsIgnoreCase(action)) {
                String updateQuery = "UPDATE customers SET initialBalance = initialBalance + ? WHERE accountNumber = ?";
                try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                    updateStatement.setDouble(1, amount);
                    updateStatement.setString(2, accountNumber);
                    updateStatement.executeUpdate();
                }

                // Insert transaction record
                String insertTransactionQuery = "INSERT INTO transactions (account_number, transaction_type, transaction_amount, transaction_date) VALUES (?, ?, ?, ?)";
                try (PreparedStatement insertStatement = connection.prepareStatement(insertTransactionQuery)) {
                    insertStatement.setString(1, accountNumber);
                    insertStatement.setString(2, "Deposit");
                    insertStatement.setDouble(3, amount);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    insertStatement.setString(4, dateFormat.format(new Date()));
                    insertStatement.executeUpdate();
                }

                success = true;
                message = "Deposit successful!";
            } else {
                message = "Invalid action.";
            }

            // Fetch transaction history
            List<Transaction> transactions = new ArrayList<>();
            String historyQuery = "SELECT transaction_type, transaction_amount, transaction_date FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";
            try (PreparedStatement historyStatement = connection.prepareStatement(historyQuery)) {
                historyStatement.setString(1, accountNumber);
                ResultSet historyResultSet = historyStatement.executeQuery();
                while (historyResultSet.next()) {
                    String type = historyResultSet.getString("transaction_type");
                    double transactionAmount = historyResultSet.getDouble("transaction_amount");
                    String date = historyResultSet.getString("transaction_date");
                    transactions.add(new Transaction(type, transactionAmount, date));
                }
            }

            request.setAttribute("transactions", transactions);
            request.setAttribute("message", message);
            request.setAttribute("success", success);
            request.getRequestDispatcher("transactionHistory.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error.");
        }
    }

    public static class Transaction {
        private String type;
        private double amount;
        private String date;

        public Transaction(String type, double amount, String date) {
            this.type = type;
            this.amount = amount;
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public double getAmount() {
            return amount;
        }

        public String getDate() {
            return date;
        }
    }
}
