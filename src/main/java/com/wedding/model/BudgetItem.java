package com.wedding.model;

/**
 * BudgetItem class representing a wedding expense
 * Encapsulation: private fields with getters and setters
 */
public class BudgetItem {
    private String expenseId;
    private String userId;
    private String category;
    private double amount;
    private String description;

    // Constructor
    public BudgetItem() {
    }

    public BudgetItem(String expenseId, String userId, String category, double amount, String description) {
        this.expenseId = expenseId;
        this.userId = userId;
        this.category = category;
        this.amount = amount;
        this.description = description;
    }

    // Getters and Setters (Encapsulation)
    public String getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(String expenseId) {
        this.expenseId = expenseId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Convert BudgetItem to file format
     */
    @Override
    public String toString() {
        return expenseId + "|" + userId + "|" + category + "|" + amount + "|" + description;
    }

    /**
     * Parse BudgetItem from file format
     */
    public static BudgetItem fromString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 5) {
            return new BudgetItem(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]), parts[4]);
        }
        return null;
    }
}
