package AdminLoginServlet.java;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DatabaseConnection.java.DatabaseConnection;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection connection = DatabaseConnection.initializeDatabase()) {
            if (isValidAdmin(connection, username, password)) {
                response.sendRedirect("adminDashboard.html");
            } else {
                response.getWriter().println("Invalid login credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error");
        }
    }

    private boolean isValidAdmin(Connection connection, String username, String password) throws SQLException {
        String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
