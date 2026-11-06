package com.wedding.servlet;

import com.wedding.model.BudgetItem;
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
 * BudgetServlet handles budget planning operations (CRUD)
 * Supports: Add Expense, View Expenses, Update Expense, Delete Expense
 */
@WebServlet("/budget")
public class BudgetServlet extends HttpServlet {
    private static final String BUDGET_FILE = "budget.txt";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action) || action == null) {
            handleList(request, response);
        } else {
            response.sendRedirect("budget.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            handleAdd(request, response);
        } else if ("update".equals(action)) {
            handleUpdate(request, response);
        } else if ("delete".equals(action)) {
            handleDelete(request, response);
        }
    }

    /**
     * CRUD: Read - List all expenses for logged-in user
     */
    private void handleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<String> lines = FileUtil.readFile(BUDGET_FILE);
        List<BudgetItem> budgetItems = new ArrayList<>();
        double totalBudget = 0;

        for (String line : lines) {
            BudgetItem item = BudgetItem.fromString(line);
            if (item != null && item.getUserId().equals(userId)) {
                budgetItems.add(item);
                totalBudget += item.getAmount();
            }
        }

        request.setAttribute("budgetItems", budgetItems);
        request.setAttribute("totalBudget", totalBudget);
        request.getRequestDispatcher("budget.jsp").forward(request, response);
    }

    /**
     * CRUD: Create - Add new expense
     */
    private void handleAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String category = request.getParameter("category");
        String amount = request.getParameter("amount");
        String description = request.getParameter("description");

        // Validate input
        if (category == null || category.isEmpty() || amount == null || amount.isEmpty() ||
            description == null || description.isEmpty()) {
            request.setAttribute("error", "All fields are required!");
            request.getRequestDispatcher("budget.jsp").forward(request, response);
            return;
        }

        try {
            double amountValue = Double.parseDouble(amount);
            
            // Create new budget item
            String expenseId = FileUtil.generateId("EXPENSE");
            BudgetItem newItem = new BudgetItem(expenseId, userId, category, amountValue, description);

            // Save to file
            if (FileUtil.appendToFile(BUDGET_FILE, newItem.toString())) {
                response.sendRedirect("budget?action=list");
            } else {
                request.setAttribute("error", "Failed to add expense. Please try again.");
                request.getRequestDispatcher("budget.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Amount must be a valid number!");
            request.getRequestDispatcher("budget.jsp").forward(request, response);
        }
    }

    /**
     * CRUD: Update - Update expense details
     */
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String expenseId = request.getParameter("expenseId");
        String category = request.getParameter("category");
        String amount = request.getParameter("amount");
        String description = request.getParameter("description");

        // Validate input
        if (expenseId == null || expenseId.isEmpty() || category == null || category.isEmpty() ||
            amount == null || amount.isEmpty() || description == null || description.isEmpty()) {
            response.sendRedirect("budget?action=list");
            return;
        }

        try {
            double amountValue = Double.parseDouble(amount);
            
            // Read all expenses and update
            List<String> lines = FileUtil.readFile(BUDGET_FILE);
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                BudgetItem item = BudgetItem.fromString(line);
                if (item != null && item.getExpenseId().equals(expenseId) && item.getUserId().equals(userId)) {
                    item.setCategory(category);
                    item.setAmount(amountValue);
                    item.setDescription(description);
                    updatedLines.add(item.toString());
                } else {
                    updatedLines.add(line);
                }
            }

            if (FileUtil.writeFile(BUDGET_FILE, updatedLines)) {
                response.sendRedirect("budget?action=list");
            } else {
                response.sendRedirect("budget?action=list");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("budget?action=list");
        }
    }

    /**
     * CRUD: Delete - Remove expense
     */
    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String expenseId = request.getParameter("expenseId");

        if (expenseId == null || expenseId.isEmpty()) {
            response.sendRedirect("budget?action=list");
            return;
        }

        // Read all expenses and remove the specific one
        List<String> lines = FileUtil.readFile(BUDGET_FILE);
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            BudgetItem item = BudgetItem.fromString(line);
            if (item == null || !item.getExpenseId().equals(expenseId) || !item.getUserId().equals(userId)) {
                updatedLines.add(line);
            }
        }

        if (FileUtil.writeFile(BUDGET_FILE, updatedLines)) {
            response.sendRedirect("budget?action=list");
        } else {
            response.sendRedirect("budget?action=list");
        }
    }
}
