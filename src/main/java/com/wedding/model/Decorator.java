package com.wedding.model;

/**
 * Decorator class extending Vendor
 * Inheritance: specific vendor type with specialized behavior
 */
public class Decorator extends Vendor {
    
    public Decorator() {
        super();
    }

    public Decorator(String vendorId, String name, String category, double price, String contact) {
        super(vendorId, name, category, price, contact);
    }

    /**
     * Polymorphic implementation
     */
    @Override
    public String getServiceDescription() {
        return "Elegant decoration and floral arrangements for your wedding day";
    }
}
