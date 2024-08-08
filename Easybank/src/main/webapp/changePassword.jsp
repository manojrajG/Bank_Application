<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank Application - Change Password</title>
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

        .change-password-container {
            background-color: #08678C;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }

        .change-password-container h2 {
            margin-bottom: 1rem;
        }

        .input-group {
            margin-bottom: 1rem;
            position: relative;
        }

        .input-group input {
            width: 100%;
            padding: 0.5rem;
            border-radius: 5px;
            border: none;
            outline: none;
        }

        .input-group .toggle-password {
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

        .error-message {
            color: red;
            margin-bottom: 1rem;
        }
    </style>
</head>
<body>
    <div class="change-password-container">
        <h2>Change Password</h2>
        <form action="ChangePasswordServlet" method="post">
            <div class="input-group">
                <input type="password" id="currentPassword" name="currentPassword" placeholder="Current Password" required>
                <span class="toggle-password" onclick="togglePasswordVisibility('currentPassword')">👁️</span>
            </div>
            <div class="input-group">
                <input type="password" id="newPassword" name="newPassword" placeholder="New Password" required>
                <span class="toggle-password" onclick="togglePasswordVisibility('newPassword')">👁️</span>
            </div>
            <div class="input-group">
                <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm Password" required>
                <span class="toggle-password" onclick="togglePasswordVisibility('confirmPassword')">👁️</span>
            </div>
            <button type="submit" class="btn">Change Password</button>
            <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
            <% if (errorMessage != null) { %>
                <div class="error-message"><%= errorMessage %></div>
            <% } %>
        </form>
    </div>
    <script>
        function togglePasswordVisibility(inputId) {
            const input = document.getElementById(inputId);
            if (input.type === "password") {
                input.type = "text";
            } else {
                input.type = "password";
            }
        }
    </script>
</body>
</html>
