package com.wedding.model;

/**
 * User class representing a wedding planner user
 * Encapsulation: private fields with getters and setters
 */
public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String weddingDate;

    // Constructor
    public User() {
    }

    public User(String id, String name, String email, String password, String weddingDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.weddingDate = weddingDate;
    }

    // Getters and Setters (Encapsulation)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWeddingDate() {
        return weddingDate;
    }

    public void setWeddingDate(String weddingDate) {
        this.weddingDate = weddingDate;
    }

    /**
     * Convert User to file format
     */
    @Override
    public String toString() {
        return id + "|" + name + "|" + email + "|" + password + "|" + weddingDate;
    }

    /**
     * Parse User from file format
     */
    public static User fromString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 5) {
            return new User(parts[0], parts[1], parts[2], parts[3], parts[4]);
        }
        return null;
    }
}
