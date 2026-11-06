<%@ include file="header.jsp" %>

<div class="my-5">
    <h2 style="color: var(--secondary); font-weight: 700; margin-bottom: 2rem;">Browse Vendors</h2>

    <div class="card mb-4">
        <div class="card-body">
            <h5 class="card-title">Search Vendors by Category</h5>
            <form action="vendor?action=search" method="GET" class="row">
                <div class="col-md-4">
                    <select name="category" class="form-select" required>
                        <option value="">Select Category</option>
                        <option value="photographer">Photographer</option>
                        <option value="caterer">Caterer</option>
                        <option value="decorator">Decorator</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <button type="submit" class="btn btn-primary w-100">Search</button>
                </div>
                <div class="col-md-2">
                    <a href="vendor?action=list" class="btn btn-outline-primary w-100">Clear Filter</a>
                </div>
            </form>
        </div>
    </div>

    <c:if test="${not empty searchCategory}">
        <div class="alert alert-info">
            Showing vendors in category: <strong>${searchCategory}</strong>
        </div>
    </c:if>

    <c:if test="${empty vendors}">
        <div class="alert alert-warning">
            No vendors found. Please try a different search.
        </div>
    </c:if>

    <c:if test="${not empty vendors}">
        <div class="row">
            <c:forEach var="vendor" items="${vendors}">
                <div class="col-md-4 mb-4">
                    <div class="card h-100">
                        <div class="card-body">
                            <h5 class="card-title" style="color: var(--primary);">${vendor.name}</h5>
                            <p class="card-text">
                                <strong>Category:</strong> ${vendor.category}<br>
                                <strong>Price:</strong> $${vendor.price}<br>
                                <strong>Contact:</strong> ${vendor.contact}
                            </p>
                        </div>
                        <div class="card-footer" style="background-color: var(--accent); border-top: none;">
                            <c:if test="${not empty sessionScope.user}">
                                <button class="btn btn-primary w-100 btn-sm" onclick="bookVendor('${vendor.vendorId}', '${vendor.name}')">Book Now</button>
                            </c:if>
                            <c:if test="${empty sessionScope.user}">
                                <a href="login.jsp" class="btn btn-primary w-100 btn-sm">Login to Book</a>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:if>
</div>

<!-- Booking Modal -->
<div class="modal fade" id="bookingModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Book Vendor</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <form action="booking" method="POST">
                <input type="hidden" name="action" value="book">
                <input type="hidden" name="vendorId" id="vendorId">
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Vendor Name</label>
                        <input type="text" class="form-control" id="vendorName" disabled>
                    </div>
                    <div class="mb-3">
                        <label for="eventDate" class="form-label">Event Date</label>
                        <input type="date" class="form-control" id="eventDate" name="eventDate" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Book Vendor</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function bookVendor(vendorId, vendorName) {
        document.getElementById('vendorId').value = vendorId;
        document.getElementById('vendorName').value = vendorName;
        
        var bookingModal = new bootstrap.Modal(document.getElementById('bookingModal'));
        bookingModal.show();
    }
</script>

<%@ include file="footer.jsp" %>
