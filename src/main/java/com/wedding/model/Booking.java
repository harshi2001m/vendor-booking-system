package com.wedding.model;

/**
 * Booking class representing a vendor booking
 * Encapsulation: private fields with getters and setters
 */
public class Booking {
    private String bookingId;
    private String userId;
    private String vendorId;
    private String vendorName;
    private String eventDate;
    private String status; // CONFIRMED, PENDING, CANCELLED

    // Constructor
    public Booking() {
    }

    public Booking(String bookingId, String userId, String vendorId, String vendorName, String eventDate, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.eventDate = eventDate;
        this.status = status;
    }

    // Getters and Setters (Encapsulation)
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Validate booking date
     */
    public boolean isValid() {
        return bookingId != null && !bookingId.isEmpty() &&
               userId != null && !userId.isEmpty() &&
               vendorId != null && !vendorId.isEmpty() &&
               eventDate != null && !eventDate.isEmpty();
    }

    /**
     * Convert Booking to file format
     */
    @Override
    public String toString() {
        return bookingId + "|" + userId + "|" + vendorId + "|" + vendorName + "|" + eventDate + "|" + status;
    }

    /**
     * Parse Booking from file format
     */
    public static Booking fromString(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 6) {
            return new Booking(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
        }
        return null;
    }
}
