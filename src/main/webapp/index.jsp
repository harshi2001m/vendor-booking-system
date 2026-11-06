<%@ include file="header.jsp" %>

<!-- Hero Section -->
<div class="hero">
    <div class="container">
        <h1>Plan Your Perfect Wedding</h1>
        <p>Find vendors, manage bookings, and plan your budget all in one place</p>
        <c:if test="${empty sessionScope.user}">
            <a href="register.jsp" class="btn btn-light btn-lg me-2">Get Started</a>
            <a href="login.jsp" class="btn btn-outline-light btn-lg">Login</a>
        </c:if>
        <c:if test="${not empty sessionScope.user}">
            <a href="vendor?action=list" class="btn btn-light btn-lg me-2">Browse Vendors</a>
            <a href="booking?action=list" class="btn btn-outline-light btn-lg">View My Bookings</a>
        </c:if>
    </div>
</div>

<!-- Features Section -->
<div class="container my-5">
    <h2 class="text-center mb-5" style="color: var(--secondary); font-weight: 700;">Our Features</h2>
    
    <div class="row">
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-body text-center">
                    <h3 style="color: var(--primary);">👥</h3>
                    <h5 class="card-title">User Management</h5>
                    <p class="card-text">Create account, manage profile, and keep track of your wedding details</p>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-body text-center">
                    <h3 style="color: var(--primary);">🎉</h3>
                    <h5 class="card-title">Vendor Booking</h5>
                    <p class="card-text">Browse photographers, caterers, and decorators. Book your favorite vendors easily</p>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-4">
            <div class="card">
                <div class="card-body text-center">
                    <h3 style="color: var(--primary);">💰</h3>
                    <h5 class="card-title">Budget Planning</h5>
                    <p class="card-text">Track expenses and manage your wedding budget with ease</p>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Statistics Section -->
<div style="background: linear-gradient(135deg, var(--primary) 0%, var(--secondary) 100%); color: white; padding: 3rem 0; margin: 3rem 0;">
    <div class="container">
        <div class="row">
            <div class="col-md-3 stats-box">
                <h3>500+</h3>
                <p>Vendors Available</p>
            </div>
            <div class="col-md-3 stats-box">
                <h3>1000+</h3>
                <p>Happy Couples</p>
            </div>
            <div class="col-md-3 stats-box">
                <h3>50+</h3>
                <p>Cities Covered</p>
            </div>
            <div class="col-md-3 stats-box">
                <h3>24/7</h3>
                <p>Customer Support</p>
            </div>
        </div>
    </div>
</div>

<!-- Call to Action Section -->
<div class="container my-5 text-center">
    <h2 style="color: var(--secondary); font-weight: 700;">Ready to Plan Your Wedding?</h2>
    <p class="mb-4">Start browsing vendors and managing your wedding plans today</p>
    <c:if test="${empty sessionScope.user}">
        <a href="register.jsp" class="btn btn-primary btn-lg me-2">Register Now</a>
        <a href="vendor?action=list" class="btn btn-outline-primary btn-lg">Browse Vendors</a>
    </c:if>
    <c:if test="${not empty sessionScope.user}">
        <p style="color: var(--primary); font-weight: 600; margin-bottom: 1rem;">Welcome, ${sessionScope.user.name}!</p>
        <a href="vendor?action=list" class="btn btn-primary btn-lg me-2">Browse Vendors</a>
        <a href="booking?action=list" class="btn btn-outline-primary btn-lg">Manage Bookings</a>
    </c:if>
</div>

<%@ include file="footer.jsp" %>
