<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank Application - Customer</title>
    <link rel="stylesheet" href="styles.css">
    <style>
        body {
            font-family: Montserrat, Georgia;
            margin: 0;
            padding: 0;
            background-color: #032859;
          
        }

        .customer-page {
            padding: 2rem;
            text-align: center;
        }

        .customer-options {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            gap: 1rem;
        }

        .option-card {
            width: 150px;
            padding: 1rem;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            background-color: #065c78;
            transition: transform 0.3s;
            color: #f4f4f4;
        }

        .option-card a {
            text-decoration: none;
            color: #fcfbfb;
            font-weight: bold;
        }

        .option-card:hover {
            transform: scale(1.05);
        }

        .content-card {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 300px;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.2);
            background-color: #065c78;
            display: none;
            z-index: 1000;
        }

        .content-card h2 {
            margin: 0 0 1rem;
            color: #08678C;
        }

        .close-btn {
            position: absolute;
            top: 10px;
            right: 10px;
            background: #333;
            color: #065c78;
            border: none;
            border-radius: 50%;
            width: 25px;
            height: 25px;
            cursor: pointer;
            text-align: center;
            line-height: 25px;
        }

        .overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.5);
            display: none;
            z-index: 999;
        }
    </style>
</head>
<body>
    <div class="customer-page">
        <div class="customer-options">
            <div class="option-card"><a href="ViewBalanceServlet">View Balance</a></div>
            <div class="option-card"><a href="changePassword.jsp">Change Password</a></div>
            <div class="option-card"><a href="transactionHistory.jsp">View Transactions</a></div>
            
            <div class="option-card"><a href="withdrawMoney.jsp">Withdraw Money</a></div>
            <div class="option-card"><a href="depositMoney.jsp">Deposit Money</a></div>            
        </div>
    </div>
    <div class="animation"></div>
</body>
</html>
