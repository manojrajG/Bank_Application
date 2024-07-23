package TransactionHistoryServlet.java;
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

@WebServlet("/TransactionHistoryServlet")
public class TransactionHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();
        String accountNumber = (String) session.getAttribute("accountNumber");

        if (accountNumber == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Easybank_app", "root", "root")) {
            String query = "SELECT transaction_type, transaction_amount, transaction_date FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, accountNumber);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            System.out.print(resultSet.getInt(0));
            while(resultSet.next()) {
            	System.out.print(resultSet.getString("transaction_type"));
            }
            
            request.setAttribute("resultSet", resultSet);
            request.getRequestDispatcher("transactionHistory.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<p>Error retrieving transaction history.</p>");
        }
    }
}
