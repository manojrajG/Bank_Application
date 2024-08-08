package RegisterCustomerServlet.java;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DatabaseConnection.java.DatabaseConnection;

@WebServlet("/RegisterCustomerServlet")
public class RegisterCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String address = request.getParameter("address");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String accountType = request.getParameter("accountType");
        int initialBalance = Integer.parseInt(request.getParameter("initialBalance"));
        String dob = request.getParameter("dob");
        String idProof = request.getParameter("idProof");

        String accountNumber = generateAccountNumber();
        String password = generateTemporaryPassword();

        try (Connection connection = DatabaseConnection.initializeDatabase()) {
            String query = "INSERT INTO customers (username, address, mobile, email, accountType, initialBalance, dob, idProof, accountNumber, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, address);
                preparedStatement.setString(3, mobile);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, accountType);
                preparedStatement.setInt(6, initialBalance);
                preparedStatement.setString(7, dob);
                preparedStatement.setString(8, idProof);
                preparedStatement.setString(9, accountNumber);
                preparedStatement.setString(10, password);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error");
            return;
        }

        response.sendRedirect("registrationSuccess.html?accountNumber=" + accountNumber + "&password=" + password);
    }

    private String generateAccountNumber() {
        Random rand = new Random();
        int number = rand.nextInt(99999999);
        return String.format("%08d", number);
    }

    private String generateTemporaryPassword() {
        Random rand = new Random();
        int number = rand.nextInt(999999);
        return String.format("%06d", number);
    }
}
