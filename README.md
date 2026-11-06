# Wedding Planning and Vendor Booking System

A complete Java-based web application using JSP and Servlets for managing wedding planning tasks, vendor bookings, and budget planning.

## Project Overview

This system provides comprehensive features for wedding planning:

### **4 Main Modules:**

1. **User Management**
   - Register new user account
   - Login with email and password
   - Update user profile
   - Delete account

2. **Vendor Management**
   - Browse vendors by category (Photographer, Caterer, Decorator)
   - Search vendors by category
   - Admin: Add new vendors
   - Admin: Update vendor details
   - Admin: Delete vendors

3. **Booking Management**
   - Book vendors for wedding events
   - View all bookings
   - Update booking dates
   - Cancel bookings

4. **Budget Planning**
   - Add wedding expenses
   - View all expenses with total budget
   - Update expense details
   - Delete expenses

## Technology Stack

- **Backend:** Java (Servlets)
- **Frontend:** JSP, Bootstrap 5
- **Data Storage:** Text files (users.txt, vendors.txt, bookings.txt, budget.txt)
- **Build Tool:** Maven
- **Java Version:** 11+
- **Server:** Apache Tomcat 9+

## Project Structure

```
wedding-planning-system/
├── pom.xml                                  # Maven configuration
├── README.md                                # This file
├── src/
│   ├── main/
│   │   ├── java/com/wedding/
│   │   │   ├── model/                       # Data models
│   │   │   │   ├── User.java
│   │   │   │   ├── Vendor.java
│   │   │   │   ├── Photographer.java        # Vendor subclass
│   │   │   │   ├── Caterer.java             # Vendor subclass
│   │   │   │   ├── Decorator.java           # Vendor subclass
│   │   │   │   ├── Booking.java
│   │   │   │   └── BudgetItem.java
│   │   │   ├── servlet/                     # Request handlers
│   │   │   │   ├── UserServlet.java
│   │   │   │   ├── VendorServlet.java
│   │   │   │   ├── BookingServlet.java
│   │   │   │   └── BudgetServlet.java
│   │   │   └── util/
│   │   │       └── FileUtil.java            # File handling utility
│   │   └── webapp/
│   │       ├── index.jsp                    # Homepage
│   │       ├── register.jsp                 # User registration
│   │       ├── login.jsp                    # User login
│   │       ├── profile.jsp                  # User profile
│   │       ├── vendors.jsp                  # Vendor listing
│   │       ├── bookings.jsp                 # Booking management
│   │       ├── budget.jsp                   # Budget planning
│   │       ├── header.jsp                   # Navigation and styles
│   │       ├── footer.jsp                   # Footer
│   │       └── WEB-INF/
│   │           └── web.xml                  # Web configuration
└── data/                                    # Data storage directory
    ├── users.txt                            # User data
    ├── vendors.txt                          # Vendor data
    ├── bookings.txt                         # Booking data
    └── budget.txt                           # Budget data
```

## OOP Principles Implemented

### 1. **Encapsulation**
- All model classes use private fields with public getters/setters
- FileUtil class encapsulates file operations
- Servlets encapsulate business logic

### 2. **Inheritance**
- `Photographer`, `Caterer`, `Decorator` classes inherit from abstract `Vendor` class
- Demonstrates parent-child relationship and code reusability

### 3. **Polymorphism**
- `getServiceDescription()` abstract method implemented differently in each Vendor subclass
- Different vendor types display custom service descriptions

### 4. **Abstraction**
- `Vendor` is an abstract base class defining common interface
- `Booking.isValid()` method provides validation abstraction
- FileUtil abstracts file operations from servlets

## Data File Format

All data is stored in `/data` folder using pipe-delimited (|) format:

### users.txt
```
id|name|email|password|weddingDate
```

### vendors.txt
```
vendorId|name|category|price|contact
```

### bookings.txt
```
bookingId|userId|vendorId|vendorName|eventDate|status
```

### budget.txt
```
expenseId|userId|category|amount|description
```

## Setup and Installation

### Prerequisites
- Java 11 or higher
- Maven 3.6+
- Tomcat 9+ server

### Steps

1. **Clone/Download Project**
   ```bash
   cd wedding-planning-system
   ```

2. **Build Project**
   ```bash
   mvn clean install
   ```

3. **Run with Tomcat Maven Plugin**
   ```bash
   mvn tomcat7:run
   ```
   The application will be available at `http://localhost:8080`

4. **Alternative: Manual Deployment**
   - Build WAR file: `mvn package`
   - Copy `target/wedding-planning.war` to Tomcat's `webapps/` directory
   - Start Tomcat

## Sample Credentials

### Test Users
- **User 1:** john@example.com / password123
- **User 2:** sarah@example.com / password456
- **User 3:** emily@example.com / password789

### Sample Vendors
- 3 Photographers
- 3 Caterers
- 3 Decorators

All with pre-configured pricing and contact information.

## Features

### ✅ User Module (CRUD Operations)
- **Create:** Register new user
- **Read:** Display user profile
- **Update:** Edit profile details and wedding date
- **Delete:** Remove user account

### ✅ Vendor Module (CRUD Operations)
- **Create:** Add new vendor (Admin)
- **Read:** List/search vendors by category
- **Update:** Modify vendor details
- **Delete:** Remove vendor

### ✅ Booking Module (CRUD Operations)
- **Create:** Book a vendor
- **Read:** View user's bookings
- **Update:** Change booking date
- **Delete:** Cancel booking

### ✅ Budget Module (CRUD Operations)
- **Create:** Add expense
- **Read:** View all expenses and total
- **Update:** Edit expense details
- **Delete:** Remove expense

## Session Management

- User login creates a session storing user object
- Session persists across page navigation
- Logout invalidates session
- Protected pages redirect to login if session is empty

## UI/UX Features

- **Responsive Design:** Works on desktop and mobile
- **Bootstrap 5:** Professional styling
- **Modal Dialogs:** For booking and editing operations
- **Form Validation:** Client-side and server-side
- **Color Scheme:** Warm wedding-themed colors (gold, brown, beige)
- **Navigation Bar:** Easy access to all modules
- **Alerts:** Success and error messages
- **Data Tables:** Clear display of records with action buttons

## Browser Compatibility

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## File Handling

The `FileUtil` class provides:
- `readFile()` - Read all records from file
- `writeFile()` - Write records back to file
- `appendToFile()` - Add new record to file
- `generateId()` - Create unique IDs with timestamp
- `clearFile()` - Empty file content

## Error Handling

- Input validation on all forms
- File operation error handling
- Session timeout handling
- Database-like consistency with file operations
- User-friendly error messages

## Performance Considerations

- File-based storage for educational purposes
- For production, consider using a proper database (MySQL, PostgreSQL)
- Caching can be added for frequently accessed vendors
- Indexing records would improve search performance

## Future Enhancements

1. Database integration (MySQL/PostgreSQL)
2. Payment integration for booking confirmations
3. Email notifications for bookings
4. Admin dashboard with analytics
5. Reviews and ratings system
6. Photo gallery for vendors
7. Guest list management
8. Seating arrangement tool
9. Timeline/checklist generator
10. PDF invoice generation

## Troubleshooting

### Issue: Files not found in `/data` folder
- Solution: Ensure `/data` folder exists in the project root or where Tomcat is running

### Issue: Servlet mapping not working
- Solution: Check that servlet URL patterns match in web.xml and ServletAnnotations

### Issue: Session not persisting
- Solution: Ensure cookies are enabled in browser

## Testing

### Test Scenarios

1. **User Registration**
   - Register with valid email
   - Try duplicate email (should fail)
   - Login with credentials

2. **Vendor Browsing**
   - View all vendors
   - Search by category
   - Book vendor from listing

3. **Booking Management**
   - Create booking
   - Update booking date
   - Cancel booking

4. **Budget Planning**
   - Add multiple expenses
   - Edit expense details
   - Delete expense
   - Verify total calculation

## Code Quality

- **Comments:** Well-commented code throughout
- **Naming Conventions:** Clear, descriptive names
- **Error Handling:** Comprehensive error handling
- **Code Organization:** Logical separation of concerns
- **OOP Principles:** Proper use of inheritance, encapsulation, polymorphism

## License

Educational project - Free to use and modify for learning purposes.

## Author

Created as a comprehensive OOP project demonstrating Java web development with Servlets and JSP.

---

**Note:** This is an educational project designed to teach OOP principles and web development. For production use, consider migrating to a database and adding additional security measures.
