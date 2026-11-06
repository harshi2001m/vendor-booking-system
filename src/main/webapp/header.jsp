<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Wedding Planning and Vendor Booking System</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <style>
        :root {
            --primary: #d4a574;
            --secondary: #8b7355;
            --accent: #f5e6d3;
            --dark: #2d2d2d;
            --light: #f9f8f5;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: var(--light);
            color: var(--dark);
        }

        .navbar {
            background: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%);
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }

        .navbar-brand {
            font-size: 1.5rem;
            font-weight: 700;
            color: white !important;
            text-transform: uppercase;
            letter-spacing: 1px;
        }

        .nav-link {
            color: rgba(255,255,255,0.9) !important;
            transition: color 0.3s ease;
            font-weight: 500;
        }

        .nav-link:hover {
            color: white !important;
        }

        .btn-primary {
            background-color: var(--primary);
            border-color: var(--primary);
        }

        .btn-primary:hover {
            background-color: var(--secondary);
            border-color: var(--secondary);
        }

        .card {
            border: none;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            transition: transform 0.3s ease;
        }

        .card:hover {
            transform: translateY(-5px);
        }

        .btn {
            transition: all 0.3s ease;
            border-radius: 0.5rem;
        }

        .alert {
            border-radius: 0.5rem;
        }

        table {
            background-color: white;
        }

        th {
            background-color: var(--accent);
            color: var(--secondary);
            font-weight: 600;
        }

        .hero {
            background: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%);
            color: white;
            padding: 80px 0;
            text-align: center;
        }

        .hero h1 {
            font-size: 3rem;
            font-weight: 700;
            margin-bottom: 1rem;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.2);
        }

        .hero p {
            font-size: 1.25rem;
            margin-bottom: 2rem;
        }

        .form-control, .form-select {
            border: 1px solid #ddd;
            border-radius: 0.5rem;
        }

        .form-control:focus, .form-select:focus {
            border-color: var(--primary);
            box-shadow: 0 0 0 0.2rem rgba(212, 165, 116, 0.25);
        }

        label {
            font-weight: 600;
            color: var(--secondary);
            margin-bottom: 0.5rem;
        }

        footer {
            background-color: var(--secondary);
            color: white;
            padding: 2rem 0;
            margin-top: 3rem;
        }

        .stats-box {
            text-align: center;
            padding: 2rem;
        }

        .stats-box h3 {
            color: var(--primary);
            font-size: 2rem;
            font-weight: 700;
        }

        .stats-box p {
            color: var(--secondary);
            font-weight: 500;
        }
    </style>
</head>
<body>
    <!-- Navigation Bar -->
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="index.jsp">💍 Wedding Planner</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="index.jsp">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="vendor?action=list">Vendors</a>
                    </li>
                    <c:if test="${not empty sessionScope.user}">
                        <li class="nav-item">
                            <a class="nav-link" href="booking?action=list">My Bookings</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="budget?action=list">Budget</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="user?action=profile">Profile</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="user?action=logout">Logout</a>
                        </li>
                    </c:if>
                    <c:if test="${empty sessionScope.user}">
                        <li class="nav-item">
                            <a class="nav-link" href="login.jsp">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="register.jsp">Register</a>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Container for page content -->
    <div class="container mt-4 mb-4">
