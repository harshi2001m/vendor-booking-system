package com.wedding.servlet;

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
 * VendorServlet handles vendor management operations (CRUD)
 * Supports: Add Vendor, View Vendors, Update Vendor, Delete Vendor
 */
@WebServlet("/vendor")
public class VendorServlet extends HttpServlet {
    private static final String VENDORS_FILE = "vendors.txt";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("list".equals(action) || action == null) {
            handleList(request, response);
        } else if ("search".equals(action)) {
            handleSearch(request, response);
        } else {
            response.sendRedirect("vendors.jsp");
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
     * CRUD: Read - List all vendors
     */
    private void handleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<String> lines = FileUtil.readFile(VENDORS_FILE);
        List<Vendor> vendors = new ArrayList<>();

        for (String line : lines) {
            Vendor vendor = Vendor.fromString(line);
            if (vendor != null) {
                vendors.add(vendor);
            }
        }

        request.setAttribute("vendors", vendors);
        request.getRequestDispatcher("vendors.jsp").forward(request, response);
    }

    /**
     * CRUD: Read - Search vendors by category
     */
    private void handleSearch(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String category = request.getParameter("category");
        List<String> lines = FileUtil.readFile(VENDORS_FILE);
        List<Vendor> vendors = new ArrayList<>();

        for (String line : lines) {
            Vendor vendor = Vendor.fromString(line);
            if (vendor != null && vendor.getCategory().equalsIgnoreCase(category)) {
                vendors.add(vendor);
            }
        }

        request.setAttribute("vendors", vendors);
        request.setAttribute("searchCategory", category);
        request.getRequestDispatcher("vendors.jsp").forward(request, response);
    }

    /**
     * CRUD: Create - Add new vendor (Admin only)
     */
    private void handleAdd(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String category = request.getParameter("category");
        String price = request.getParameter("price");
        String contact = request.getParameter("contact");

        // Validate input
        if (name == null || name.isEmpty() || category == null || category.isEmpty() ||
            price == null || price.isEmpty() || contact == null || contact.isEmpty()) {
            request.setAttribute("error", "All fields are required!");
            request.getRequestDispatcher("addVendor.jsp").forward(request, response);
            return;
        }

        try {
            double priceValue = Double.parseDouble(price);
            
            // Create new vendor
            String vendorId = FileUtil.generateId("VENDOR");
            Vendor newVendor = new Vendor(vendorId, name, category, priceValue, contact) {
                @Override
                public String getServiceDescription() {
                    return "Professional " + category + " services";
                }
            };

            // Save to file
            if (FileUtil.appendToFile(VENDORS_FILE, newVendor.toString())) {
                request.setAttribute("success", "Vendor added successfully!");
                response.sendRedirect("vendor?action=list");
            } else {
                request.setAttribute("error", "Failed to add vendor. Please try again.");
                request.getRequestDispatcher("addVendor.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Price must be a valid number!");
            request.getRequestDispatcher("addVendor.jsp").forward(request, response);
        }
    }

    /**
     * CRUD: Update - Update vendor details
     */
    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vendorId = request.getParameter("vendorId");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String contact = request.getParameter("contact");

        // Validate input
        if (vendorId == null || vendorId.isEmpty() || name == null || name.isEmpty() ||
            price == null || price.isEmpty() || contact == null || contact.isEmpty()) {
            request.setAttribute("error", "All fields are required!");
            response.sendRedirect("vendor?action=list");
            return;
        }

        try {
            double priceValue = Double.parseDouble(price);
            
            // Read all vendors and update
            List<String> lines = FileUtil.readFile(VENDORS_FILE);
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                Vendor vendor = Vendor.fromString(line);
                if (vendor != null && vendor.getVendorId().equals(vendorId)) {
                    vendor.setName(name);
                    vendor.setPrice(priceValue);
                    vendor.setContact(contact);
                    updatedLines.add(vendor.toString());
                } else {
                    updatedLines.add(line);
                }
            }

            if (FileUtil.writeFile(VENDORS_FILE, updatedLines)) {
                response.sendRedirect("vendor?action=list");
            } else {
                request.setAttribute("error", "Update failed. Please try again.");
                response.sendRedirect("vendor?action=list");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Price must be a valid number!");
            response.sendRedirect("vendor?action=list");
        }
    }

    /**
     * CRUD: Delete - Remove vendor
     */
    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vendorId = request.getParameter("vendorId");

        if (vendorId == null || vendorId.isEmpty()) {
            response.sendRedirect("vendor?action=list");
            return;
        }

        // Read all vendors and remove the specific one
        List<String> lines = FileUtil.readFile(VENDORS_FILE);
        List<String> updatedLines = new ArrayList<>();

        for (String line : lines) {
            Vendor vendor = Vendor.fromString(line);
            if (vendor == null || !vendor.getVendorId().equals(vendorId)) {
                updatedLines.add(line);
            }
        }

        if (FileUtil.writeFile(VENDORS_FILE, updatedLines)) {
            response.sendRedirect("vendor?action=list");
        } else {
            response.sendRedirect("vendor?action=list");
        }
    }
}
