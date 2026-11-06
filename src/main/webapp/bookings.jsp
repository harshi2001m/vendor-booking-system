<%@ include file="header.jsp" %>

<c:if test="${empty sessionScope.user}">
    <c:redirect url="login.jsp"/>
</c:if>

<div class="my-5">
    <h2 style="color: var(--secondary); font-weight: 700; margin-bottom: 2rem;">My Bookings</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <div class="mb-3">
        <a href="vendor?action=list" class="btn btn-primary">Browse More Vendors</a>
    </div>

    <c:if test="${empty bookings}">
        <div class="alert alert-info">
            You haven't booked any vendors yet. <a href="vendor?action=list">Browse vendors</a> to get started!
        </div>
    </c:if>

    <c:if test="${not empty bookings}">
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Booking ID</th>
                        <th>Vendor Name</th>
                        <th>Event Date</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="booking" items="${bookings}">
                        <tr>
                            <td>${booking.bookingId}</td>
                            <td>${booking.vendorName}</td>
                            <td>${booking.eventDate}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${booking.status eq 'CONFIRMED'}">
                                        <span class="badge bg-success">Confirmed</span>
                                    </c:when>
                                    <c:when test="${booking.status eq 'PENDING'}">
                                        <span class="badge bg-warning">Pending</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-secondary">${booking.status}</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <button class="btn btn-sm btn-warning" onclick="editBooking('${booking.bookingId}', '${booking.eventDate}')">Edit</button>
                                <form action="booking" method="POST" style="display: inline;">
                                    <input type="hidden" name="action" value="cancel">
                                    <input type="hidden" name="bookingId" value="${booking.bookingId}">
                                    <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Cancel this booking?')">Cancel</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>

<!-- Edit Booking Modal -->
<div class="modal fade" id="editBookingModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Edit Booking</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <form action="booking" method="POST">
                <input type="hidden" name="action" value="update">
                <input type="hidden" name="bookingId" id="bookingId">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="editEventDate" class="form-label">Event Date</label>
                        <input type="date" class="form-control" id="editEventDate" name="eventDate" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary">Update Booking</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function editBooking(bookingId, eventDate) {
        document.getElementById('bookingId').value = bookingId;
        document.getElementById('editEventDate').value = eventDate;
        
        var editModal = new bootstrap.Modal(document.getElementById('editBookingModal'));
        editModal.show();
    }
</script>

<%@ include file="footer.jsp" %>
