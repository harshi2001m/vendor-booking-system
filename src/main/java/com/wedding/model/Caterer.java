package com.wedding.model;

/**
 * Caterer class extending Vendor
 * Inheritance: specific vendor type with specialized behavior
 */
public class Caterer extends Vendor {
    
    public Caterer() {
        super();
    }

    public Caterer(String vendorId, String name, String category, double price, String contact) {
        super(vendorId, name, category, price, contact);
    }

    /**
     * Polymorphic implementation
     */
    @Override
    public String getServiceDescription() {
        return "Gourmet catering services for your wedding reception and events";
    }
}
