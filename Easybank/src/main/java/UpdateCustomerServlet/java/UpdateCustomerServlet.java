package UpdateCustomerServlet.java;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DatabaseConnection.java.DatabaseConnection;

@WebServlet("/UpdateCustomerServlet")
public class UpdateCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        String username = request.getParameter("username");
        String address = request.getParameter("address");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        String accountType = request.getParameter("accountType");
        String dob = request.getParameter("dob");
        String idProof = request.getParameter("idProof");

        try (Connection connection = DatabaseConnection.initializeDatabase()) {
            String updateQuery = "UPDATE customers SET username = ?, address = ?, mobile = ?, email = ?, accountType = ?, dob = ?, idProof = ? WHERE accountNumber = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, address);
                preparedStatement.setString(3, mobile);
                preparedStatement.setString(4, email);
                preparedStatement.setString(5, accountType);
                preparedStatement.setString(6, dob);
                preparedStatement.setString(7, idProof);
                preparedStatement.setString(8, accountNumber);

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    response.sendRedirect("modifySuccess.html");
                } else {
                    response.getWriter().write("fail");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("error");
        }
    }
}
