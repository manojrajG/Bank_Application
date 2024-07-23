package ViewBalanceServlet.java;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DatabaseConnection.java.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/ViewBalanceServlet")
public class ViewBalanceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = (String) request.getSession().getAttribute("accountNumber");

        try (Connection connection = DatabaseConnection.initializeDatabase()) {
            String query = "SELECT initialBalance FROM customers WHERE accountNumber = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, accountNumber);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    double balance = rs.getDouble("initialBalance");
                    request.setAttribute("accountNumber", accountNumber);
                    request.setAttribute("balance", balance);
                    request.getRequestDispatcher("viewBalance.jsp").forward(request, response);
                } else {
                    request.setAttribute("errorMessage", "Account number not found.");
                    request.getRequestDispatcher("viewBalance.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Database error: " + e.getMessage());
            request.getRequestDispatcher("viewBalance.jsp").forward(request, response);
        }
    }
}
