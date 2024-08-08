package CustomerLoginServlet.java;import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DatabaseConnection.java.DatabaseConnection;

@WebServlet("/CustomerLoginServlet")
public class CustomerLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        String password = request.getParameter("password");

        try (Connection connection = DatabaseConnection.initializeDatabase()) {
            String query = "SELECT * FROM customers WHERE accountNumber = ? AND password = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, accountNumber);
                preparedStatement.setString(2, password);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    HttpSession session = request.getSession();
                    session.setAttribute("accountNumber", accountNumber);
                    response.sendRedirect("customerDashboard.jsp");
                } else {
                    request.setAttribute("errorMessage", "Invalid account number or password");
                    request.getRequestDispatcher("customerLogin.html").forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error");
        }
    }
}
