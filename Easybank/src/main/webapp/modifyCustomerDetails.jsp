<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modify Customer Details</title>
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

        .form-group {
            margin-bottom: 1rem;
            position: relative;
        }

        .form-group input,
        .form-group select {
            width: 100%;
            padding: 0.5rem;
            border-radius: 5px;
            border: none;
            outline: none;
        }

        .form-group .toggle-password {
            position: absolute;
            right: 0.5rem;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            color: #fff;
        }

        .btn {
            background-color: #032859;
            color: #fff;
            padding: 0.5rem 1rem;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .btn:hover {
            background-color: #34495e;
        }
    </style>
</head>
<body>
    <div class="modify-details-container">
        <h2>Modify Customer Details</h2>
        <div class="details">
            <form>
                <h3>Old Details</h3>
                <label>Account Number: ${accountNumber}</label><br>
                <label>Full Name: ${username}</label><br>
                <label>Address: ${address}</label><br>
                <label>Mobile No: ${mobile}</label><br>
                <label>Email ID: ${email}</label><br>
                <label>Account Type: ${accountType}</label><br>
                <label>Initial Balance: ${initialBalance}</label><br>
                <label>Date of Birth: ${dob}</label><br>
                <label>ID Proof: ${idProof}</label>
            </form>
            <form action="UpdateCustomerServlet" method="post">
                <h3>New Details</h3>
                <input type="hidden" name="accountNumber" value="${accountNumber}">
                <div class="form-group">
                    <input type="text" name="username" placeholder="Full Name" required>
                </div>
                <div class="form-group">
                    <input type="text" name="address" placeholder="Address" required>
                </div>
                <div class="form-group">
                    <input type="text" name="mobile" placeholder="Mobile No" required>
                </div>
                <div class="form-group">
                    <input type="email" name="email" placeholder="Email ID" required>
                </div>
                <div class="form-group">
                    <select name="accountType" required>
                        <option value="Saving">Saving Account</option>
                        <option value="Current">Current Account</option>
                    </select>
                </div>
                <div class="form-group">
                    <input type="date" name="dob" required>
                </div>
                <div class="form-group">
                    
                    <select id="idProof" name="idProof" required>
                        <option value="Aadhar Card">Aadhar Card</option>
                        <option value="PAN Card">PAN Card</option>
                        <option value="Driving License">Driving License</option>
                    </select>
                </div>
                <button class="btn" type="submit">Update</button>
            </form>
        </div>
    </div>
</body>
</html>
