<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Deposit Money</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        body {
            font-family: Montserrat, Georgia;
            margin: 0;
            padding: 0;
            background-color: #032859;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #fff;
        }

        .modify-details-container {
            background-color: #08678C;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 400px;
            text-align: center;
        }

        .modify-details-container h1 {
            color: #ffffff;
            margin-bottom: 1rem;
        }

        .form-group {
            margin-bottom: 1rem;
            text-align: left;
        }

        .form-group label {
            display: block;
            margin-bottom: 0.5rem;
            color: #ffffff;
        }

        .form-group input {
            width: 100%;
            padding: 0.5rem;
            border-radius: 5px;
            border: none;
            outline: none;
        }

        .form-group input[type="text"] {
            background-color: #fff;
            color: #000;
        }

        .btn {
            background-color: #032859;
            color: #fff;
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            margin-top: 1rem;
        }

        .btn:hover {
            background-color: #34495e;
        }

        .error-message {
            margin-top: 1rem;
            color: #ff0000; /* Red color for error messages */
        }

        .back-link {
            margin-top: 1rem;
        }

        .back-link a {
            color: #fff;
            text-decoration: none;
            font-size: 1rem;
        }

        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="modify-details-container">
        <h1 class="title">Deposit Money</h1>
        <form action="DepositMoneyServlet" method="post">
            <div class="form-group">
                <label for="amount">Amount to Deposit:</label>
                <input type="text" id="amount" name="amount" required>
            </div>
            <div class="form-group">
                <button type="submit" class="btn">Deposit</button>
            </div>
            <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
            <% if (errorMessage != null) { %>
                <div class="error-message"><%= errorMessage %></div>
            <% } %>
        </form>
        <div class="back-link">
            <a href="customerDashboard.jsp">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
