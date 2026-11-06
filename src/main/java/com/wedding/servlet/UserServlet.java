package com.wedding.servlet;

import com.wedding.model.User;
import com.wedding.util.FileUtil;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserServlet handles user management operations (CRUD)
 * Supports: Register, Login, Update Profile, Delete User
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final String USERS_FILE = "users.txt";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("logout".equals(action)) {
            HttpSession session = request.getSession();
            session.invalidate();
            response.sendRedirect("index.jsp");
        } else if ("profile".equals(action)) {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("profile.jsp").forward(request, response);
            } else {
                response.sendRedirect("login.jsp");
            }
        } else {
            response.sendRedirect("index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("register".equals(action)) {
            handleRegister(request, response);
        } else if ("login".equals(action)) {
            handleLogin(request, response);
        } else if ("update".equals(action)) {
            handleUpdate(request, response);
        } else if ("delete".equals(action)) {
            handleDelete(request, response);
        }
    }

    /**
     * CRUD: Create - Register a new user
     */
    private void handleRegister(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String weddingDate = request.getParameter("weddingDate");

        // Validate input
        if (name == null || name.isEmpty() || email == null || email.isEmpty() ||
            password == null || password.isEmpty() || weddingDate == null || weddingDate.isEmpty()) {
            request.setAttribute("error", "All fields are required!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Check if email already exists
        List<String> lines = FileUtil.readFile(USERS_FILE);
        for (String line : lines) {
            User existingUser = User.fromString(line);
            if (existingUser != null && existingUser.getEmail().equals(email)) {
                request.setAttribute("error", "Email already registered!");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
        }

        // Create new user
        String userId = FileUtil.generateId("USER");
        User newUser = new User(userId, name, email, password, weddingDate);

        // Save to file
        if (FileUtil.appendToFile(USERS_FILE, newUser.toString())) {
            request.setAttribute("success", "Registration successful! Please login.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Registration failed. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    /**
     * Read/Authenticate - Login user
     */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Validate input
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Email and password required!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        // Read users from file
        List<String> lines = FileUtil.readFile(USERS_FILE);
        for (String line : lines) {
            User user = User.fromString(line);
            if (user != null && user.getEmail().equals(email) && user.getPassword().equals(password)) {
                // Login successful - create session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("userId", user.getId());
                response.sendRedirect("dashboard.jsp");
                return;
            }
        }

        // Login failed
        request.setAttribute("error", "Invalid email or password!");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /**
     * CRUD: Update - Update user profile
     */
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String name = request.getParameter("name");
        String weddingDate = request.getParameter("weddingDate");

        // Validate input
        if (name == null || name.isEmpty() || weddingDate == null || weddingDate.isEmpty()) {
            request.setAttribute("error", "All fields are required!");
            request.setAttribute("user", sessionUser);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }

        // Update user object
        sessionUser.setName(name);
        sessionUser.setWeddingDate(weddingDate);

        // Read all users and update the specific one
        List<String> lines = FileUtil.readFile(USERS_FILE);
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            User user = User.fromString(line);
            if (user != null && user.getId().equals(sessionUser.getId())) {
                updatedLines.add(sessionUser.toString());
            } else {
                updatedLines.add(line);
            }
        }

        // Write back to file
        if (FileUtil.writeFile(USERS_FILE, updatedLines)) {
            session.setAttribute("user", sessionUser);
            request.setAttribute("success", "Profile updated successfully!");
            request.setAttribute("user", sessionUser);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        } else {
            request.setAttribute("error", "Update failed. Please try again.");
            request.setAttribute("user", sessionUser);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }

    /**
     * CRUD: Delete - Delete user account
     */
    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("user");

        if (sessionUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Read all users and remove the specific one
        List<String> lines = FileUtil.readFile(USERS_FILE);
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            User user = User.fromString(line);
            if (user == null || !user.getId().equals(sessionUser.getId())) {
                updatedLines.add(line);
            }
        }

        // Write back to file
        if (FileUtil.writeFile(USERS_FILE, updatedLines)) {
            session.invalidate();
            response.sendRedirect("index.jsp?deleted=true");
        } else {
            request.setAttribute("error", "Delete failed. Please try again.");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }
}
