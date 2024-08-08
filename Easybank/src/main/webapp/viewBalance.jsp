<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank Application - View Balance</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        body {
            font-family: Montserrat, Georgia;
            margin: 0;
            padding: 0;
            background-color: #032859;
        }

        .balance-page {
            padding: 2rem;
            text-align: center;
        }

        .balance-card {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 300px;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
            background-color: #08678C;
        }

        .balance-card h2 {
            margin: 0 0 1rem;
            color: #ffffff;
        }
        .balance-card p {
            font-size: 1.2rem;
            color: #ffffff;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            color: #ffffff;
        }
        .form-group input {
            width: calc(100% - 16px);
            padding: 8px;
            box-sizing: border-box;
            margin-bottom: 1rem;
            border: none;
            border-radius: 5px;
        }
        .form-group button {
            padding: 10px 20px;
            background-color: #007bff;
            border: none;
            color: white;
            cursor: pointer;
            border-radius: 5px;
        }
        .form-group button:hover {
            background-color: #0056b3;
        }
        .error-message {
            color: red;
            text-align: center;
            margin-bottom: 15px;
        }
        .back-link {
            display: block;
            text-align: center;
            margin-top: 20px;
        }
        .back-link a {
            color: #ffffff;
            text-decoration: none;
        }
        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="overlay" id="overlay" style="display:none;"></div>
    <div class="balance-card" id="balanceCard">
        <button class="close-btn" onclick="closeBalance()">×</button>
        <h2>View Balance</h2>

        <% String accountNumber = (String) request.getAttribute("accountNumber"); %>
        <% Double balance = (Double) request.getAttribute("balance"); %>
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (accountNumber != null && balance != null) { %>
            <p>Account Number: <%= accountNumber %></p>
            <p>Current Balance: $<%= String.format("%.2f", balance) %></p>
        <% } %>
        <% if (errorMessage != null) { %>
            <div class="error-message"><%= errorMessage %></div>
        <% } %>
        <div class="back-link">
            <a href="customerDashboard.jsp">Back to Dashboard</a>
        </div>
    </div>

    <script>
        function showBalance() {
            const balanceCard = document.getElementById('balanceCard');
            const overlay = document.getElementById('overlay');

            balanceCard.style.display = 'block';
            overlay.style.display = 'block';
        }

        function closeBalance() {
            const balanceCard = document.getElementById('balanceCard');
            const overlay = document.getElementById('overlay');

            balanceCard.style.display = 'none';
            overlay.style.display = 'none';
        }

        // Automatically show the balance card when the page loads
        document.addEventListener('DOMContentLoaded', showBalance);
    </script>
</body>
</html>
