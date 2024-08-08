<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction History</title>
    <style>
        body {
            font-family: Montserrat, Georgia;
            margin: 0;
            padding: 0;
            background-color: #032859;
            color: #fff;
        }
        .container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
        }
        h1 {
            text-align: center;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #08678C;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
            color: #000;
        }
        tr:hover {
            background-color: #ddd;
            color: #000;
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Transaction History</h1>
        <table>
            <thead>
                <tr>
                    <th>Transaction ID</th>
                    <th>Account Number</th>
                    <th>Type</th>
                    <th>Amount (₹)</th>
                    <th>Date</th>
                    <th>Time</th>
                </tr>
            </thead>
            <tbody>
                <%
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

                    String accountNumber = (String) session.getAttribute("accountNumber");
                    System.out.println(accountNumber);

                    if (accountNumber == null) {
                        out.println("<p>Session expired or not logged in.</p>");
                    } else {
                        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Easybank_app", "root", "root")) {
                            String query = "SELECT transaction_id, transaction_type, transaction_amount, transaction_date FROM transactions WHERE account_number = ? ORDER BY transaction_date DESC";
                            PreparedStatement statement = connection.prepareStatement(query);
                            statement.setString(1, accountNumber);
                            ResultSet resultSet = statement.executeQuery();

                            while (resultSet.next()) {
                                int transactionId = resultSet.getInt("transaction_id");
                                String transactionType = resultSet.getString("transaction_type");
                                double transactionAmount = resultSet.getDouble("transaction_amount");
                                Timestamp transactionDate = resultSet.getTimestamp("transaction_date");
                                String date = dateFormat.format(transactionDate);
                                String time = timeFormat.format(transactionDate);
                				%>
                <tr>
                    <td><%= transactionId %></td>
                    <td><%= accountNumber %></td>
                    <td><%= transactionType %></td>
                    <td>₹<%= transactionAmount %></td>
                    <td><%= date %></td>
                    <td><%= time %></td>
                </tr>
                				<%
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            out.println("<p>Error retrieving transaction history.</p>");
                        }
                    }
               					 %>
            </tbody>
        </table>
        <div class="back-link">
            <a href="customerDashboard.jsp">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
