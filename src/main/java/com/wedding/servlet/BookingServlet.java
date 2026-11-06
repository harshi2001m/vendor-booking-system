package com.wedding.servlet;

import com.wedding.model.Booking;
import com.wedding.model.Vendor;
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
 * BookingServlet handles booking management operations (CRUD)
 * Supports: Book Vendor, View Bookings, Update Booking, Cancel Booking
 */
@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    private static final String BOOKINGS_FILE = "bookings.txt";
    private static final String VENDORS_FILE = "vendors.txt";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action) || action == null) {
            handleList(request, response);
        } else {
            response.sendRedirect("bookings.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("book".equals(action)) {
            handleBook(request, response);
        } else if ("update".equals(action)) {
            handleUpdate(request, response);
        } else if ("cancel".equals(action)) {
            handleCancel(request, response);
        }
    }

    /**
     * CRUD: Read - List all bookings for logged-in user
     */
    private void handleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        List<String> lines = FileUtil.readFile(BOOKINGS_FILE);
        List<Booking> bookings = new ArrayList<>();

        for (String line : lines) {
            Booking booking = Booking.fromString(line);
            if (booking != null && booking.getUserId().equals(userId)) {
                bookings.add(booking);
            }
        }

        request.setAttribute("bookings", bookings);
        request.getRequestDispatcher("bookings.jsp").forward(request, response);
    }

    /**
     * CRUD: Create - Book a vendor
     */
    private void handleBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String vendorId = request.getParameter("vendorId");
        String eventDate = request.getParameter("eventDate");

        // Validate input
        if (vendorId == null || vendorId.isEmpty() || eventDate == null || eventDate.isEmpty()) {
            request.setAttribute("error", "All fields are required!");
            request.getRequestDispatcher("bookings.jsp").forward(request, response);
            return;
        }

        // Find vendor name
        String vendorName = "";
        List<String> vendorLines = FileUtil.readFile(VENDORS_FILE);
        for (String line : vendorLines) {
            Vendor vendor = Vendor.fromString(line);
            if (vendor != null && vendor.getVendorId().equals(vendorId)) {
                vendorName = vendor.getName();
                break;
            }
        }

        if (vendorName.isEmpty()) {
            request.setAttribute("error", "Vendor not found!");
            request.getRequestDispatcher("bookings.jsp").forward(request, response);
            return;
        }

        // Create new booking
        String bookingId = FileUtil.generateId("BOOKING");
        Booking newBooking = new Booking(bookingId, userId, vendorId, vendorName, eventDate, "CONFIRMED");

        // Validate booking
        if (!newBooking.isValid()) {
            request.setAttribute("error", "Invalid booking data!");
            request.getRequestDispatcher("bookings.jsp").forward(request, response);
            return;
        }

        // Save to file
        if (FileUtil.appendToFile(BOOKINGS_FILE, newBooking.toString())) {
            response.sendRedirect("booking?action=list");
        } else {
            request.setAttribute("error", "Failed to create booking. Please try again.");
            request.getRequestDispatcher("bookings.jsp").forward(request, response);
        }
    }

    /**
     * CRUD: Update - Update booking date/details
     */
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String bookingId = request.getParameter("bookingId");
        String eventDate = request.getParameter("eventDate");

        // Validate input
        if (bookingId == null || bookingId.isEmpty() || eventDate == null || eventDate.isEmpty()) {
            response.sendRedirect("booking?action=list");
            return;
        }

        // Read all bookings and update
        List<String> lines = FileUtil.readFile(BOOKINGS_FILE);
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            Booking booking = Booking.fromString(line);
            if (booking != null && booking.getBookingId().equals(bookingId) && booking.getUserId().equals(userId)) {
                booking.setEventDate(eventDate);
                updatedLines.add(booking.toString());
            } else {
                updatedLines.add(line);
            }
        }

        if (FileUtil.writeFile(BOOKINGS_FILE, updatedLines)) {
            response.sendRedirect("booking?action=list");
        } else {
            response.sendRedirect("booking?action=list");
        }
    }

    /**
     * CRUD: Delete - Cancel booking
     */
    private void handleCancel(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String bookingId = request.getParameter("bookingId");

        if (bookingId == null || bookingId.isEmpty()) {
            response.sendRedirect("booking?action=list");
            return;
        }

        // Read all bookings and remove the specific one
        List<String> lines = FileUtil.readFile(BOOKINGS_FILE);
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            Booking booking = Booking.fromString(line);
            if (booking == null || !booking.getBookingId().equals(bookingId) || !booking.getUserId().equals(userId)) {
                updatedLines.add(line);
            }
        }

        if (FileUtil.writeFile(BOOKINGS_FILE, updatedLines)) {
            response.sendRedirect("booking?action=list");
        } else {
            response.sendRedirect("booking?action=list");
        }
    }
}
