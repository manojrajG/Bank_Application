package CloseAccountServlet.java;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/CloseAccountServlet")
public class CloseAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        String accountNumber = (String) session.getAttribute("accountNumber");

        if (accountNumber == null) {
            response.sendRedirect("login.jsp"); // Redirect to login if not logged in
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Easybank_app", "root", "root")) {
            // Check if account has balance before closing
            String balanceQuery = "SELECT initialBalance FROM customers WHERE accountNumber = ?";
            PreparedStatement balanceStatement = connection.prepareStatement(balanceQuery);
            balanceStatement.setString(1, accountNumber);
            ResultSet balanceResult = balanceStatement.executeQuery();

            if (balanceResult.next()) {
                double initialBalance = balanceResult.getDouble("initialBalance");
                if (initialBalance > 0) {
                    out.println("<p>Cannot close account with a positive balance. Please withdraw all funds first.</p>");
                    return;
                }
            }

            // Close the account
            String closeQuery = "DELETE FROM customers WHERE accountNumber = ?";
            PreparedStatement closeStatement = connection.prepareStatement(closeQuery);
            closeStatement.setString(1, accountNumber);
            int rowsAffected = closeStatement.executeUpdate();

            if (rowsAffected > 0) {
                out.println("<p>Account closed successfully.</p>");
                // Invalidate session and redirect to login after closing account
                session.invalidate();
                response.sendRedirect("login.jsp");
            } else {
                out.println("<p>Failed to close account. Please try again later.</p>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error closing account: " + e.getMessage() + "</p>");
        }
    }
}
