package com.wedding.model;

/**
 * Photographer class extending Vendor
 * Inheritance: specific vendor type with specialized behavior
 */
public class Photographer extends Vendor {
    
    public Photographer() {
        super();
    }

    public Photographer(String vendorId, String name, String category, double price, String contact) {
        super(vendorId, name, category, price, contact);
    }

    /**
     * Polymorphic implementation
     */
    @Override
    public String getServiceDescription() {
        return "Professional photography services capturing your special moments";
    }
}
