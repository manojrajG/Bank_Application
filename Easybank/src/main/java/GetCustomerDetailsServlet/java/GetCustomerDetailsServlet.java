package GetCustomerDetailsServlet.java;

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

@WebServlet("/GetCustomerDetails")
public class GetCustomerDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountNumber = request.getParameter("accountNumber");
        
        try (Connection connection = DatabaseConnection.initializeDatabase()) {
            String query = "SELECT * FROM customers WHERE accountNumber = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, accountNumber);
                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    request.setAttribute("accountNumber", rs.getString("accountNumber"));
                    request.setAttribute("username", rs.getString("username"));
                    request.setAttribute("address", rs.getString("address"));
                    request.setAttribute("mobile", rs.getString("mobile"));
                    request.setAttribute("email", rs.getString("email"));
                    request.setAttribute("accountType", rs.getString("accountType"));
                    request.setAttribute("initialBalance", rs.getString("initialBalance"));
                    request.setAttribute("dob", rs.getString("dob"));
                    request.setAttribute("idProof", rs.getString("idProof"));
                    request.getRequestDispatcher("modifyCustomerDetails.jsp").forward(request, response);
                } else {
                    response.getWriter().println("Customer not found");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Database error");
        }
    }
}
