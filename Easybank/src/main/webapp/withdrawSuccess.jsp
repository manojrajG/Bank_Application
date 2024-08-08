<!DOCTYPE html>
<html>
<head>
    <title>Withdrawal Successful</title>
     <style>
        body {
            font-family: Montserrat, Georgia;
            margin: 0;
            padding: 0;
            background-color: #011126;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #fff;
            text-align: center;
        }

        .success-container {
            background-color: #08678C;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 400px;
        }

        .success-container h1 {
            margin-bottom: 1rem;
            font-size: 2rem;
        }

        .success-container p {
            font-size: 1.2rem;
            margin-bottom: 2rem;
        }

        .btn {
            background-color: #011126;
            color: #fff;
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
            text-decoration: none;
        }

        .btn:hover {
            background-color: #fdfeff;
            color: #011126;
        }
    </style>
</head>
<body>
    <div class="success-container">
        <h1>Withdrawal Successful</h1>
        <p>Your withdrawal was successful.</p>
        <div class="back-link">
            <a href="customerDashboard.jsp" class="btn">Back to Dashboard</a>
        </div>
    </div>
</body>
</html>
