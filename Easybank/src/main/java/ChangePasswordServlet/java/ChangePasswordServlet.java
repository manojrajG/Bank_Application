package ChangePasswordServlet.java;

import java.io.IOException;
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

@WebServlet("/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        String accountNumber = (String) session.getAttribute("accountNumber");

        if (accountNumber == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "New password and confirmation password do not match.");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Easybank_app", "root", "root")) {
            String query = "SELECT password FROM customers WHERE accountNumber = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");

                if (!storedPassword.equals(currentPassword)) {
                    request.setAttribute("errorMessage", "Current password is incorrect.");
                    request.getRequestDispatcher("changePassword.jsp").forward(request, response);
                    return;
                }

                String updateQuery = "UPDATE customers SET password = ? WHERE accountNumber = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                updateStatement.setString(1, newPassword);
                updateStatement.setString(2, accountNumber);
                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    response.sendRedirect("changePasswordSuccess.jsp");
                } else {
                    request.setAttribute("errorMessage", "Error updating password. Please try again.");
                    request.getRequestDispatcher("changePassword.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("errorMessage", "Account not found.");
                request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error connecting to the database. Please try again later.");
            request.getRequestDispatcher("changePassword.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
