<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Confirm Delete Customer</title>
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

        .modify-details-container h2 {
            margin-bottom: 1rem;
        }

        .details {
            margin-bottom: 1rem;
            text-align: left;
        }

        .details label {
            display: block;
            margin-bottom: 0.5rem;
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
    </style>
</head>
<body>
    <div class="modify-details-container">
        <h2>Confirm Delete Customer</h2>
        <div class="details">
            <h3>Customer Details</h3>
            <label>Account Number: ${accountNumber}</label>
            <label>Full Name: ${username}</label>
            <label>Address: ${address}</label>
            <label>Mobile No: ${mobile}</label>
            <label>Email ID: ${email}</label>
            <label>Account Type: ${accountType}</label>
            <label>Initial Balance: ${initialBalance}</label>
            <label>Date of Birth: ${dob}</label>
            <label>ID Proof: ${idProof}</label>
        </div>
        <form action="DeleteCustomerServlet" method="post">
            <input type="hidden" name="accountNumber" value="${accountNumber}">
            <button type="submit" class="btn">Confirm Delete</button>
        </form>
    </div>
</body>
</html>
