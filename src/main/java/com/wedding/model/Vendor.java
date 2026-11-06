package com.wedding.model;

/**
 * Abstract Vendor class implementing OOP principles
 * Abstraction: base class for different vendor types
 * Encapsulation: private fields with getters and setters
 */
public abstract class Vendor {
    private String vendorId;
    private String name;
    private String category;
    private double price;
    private String contact;

    // Constructor
    public Vendor() {
    }

    public Vendor(String vendorId, String name, String category, double price, String contact) {
        this.vendorId = vendorId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.contact = contact;
    }

    // Getters and Setters (Encapsulation)
    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * Abstract method for polymorphic behavior
     */
    public abstract String getServiceDescription();

    /**
     * Convert Vendor to file format
     */
    @Override
    public String toString() {
        return vendorId + "|" + name + "|" + category + "|" + price + "|" + contact;
    }

    /**
     * Parse Vendor from file format - creates concrete instance
     */
    public static Vendor fromString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 5) {
            String category = parts[2];
            double price = Double.parseDouble(parts[3]);
            
            if ("photographer".equalsIgnoreCase(category)) {
                return new Photographer(parts[0], parts[1], category, price, parts[4]);
            } else if ("caterer".equalsIgnoreCase(category)) {
                return new Caterer(parts[0], parts[1], category, price, parts[4]);
            } else if ("decorator".equalsIgnoreCase(category)) {
                return new Decorator(parts[0], parts[1], category, price, parts[4]);
            }
        }
        return null;
    }
}
