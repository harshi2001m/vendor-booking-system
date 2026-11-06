<%@ include file="header.jsp" %>

<c:if test="${empty sessionScope.user}">
    <c:redirect url="login.jsp"/>
</c:if>

<div class="row my-5">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header" style="background-color: var(--accent); border-color: var(--primary);">
                <h3 class="mb-0" style="color: var(--secondary);">My Profile</h3>
            </div>
            <div class="card-body">
                <c:if test="${not empty error}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        ${error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <c:if test="${not empty success}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        ${success}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                </c:if>

                <form action="user" method="POST" id="updateForm">
                    <input type="hidden" name="action" value="update">

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">User ID</label>
                            <input type="text" class="form-control" value="${sessionScope.user.id}" disabled>
                        </div>
                        <div class="col-md-6">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" value="${sessionScope.user.email}" disabled>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="name" class="form-label">Full Name</label>
                        <input type="text" class="form-control" id="name" name="name" value="${sessionScope.user.name}" required>
                    </div>

                    <div class="mb-3">
                        <label for="weddingDate" class="form-label">Wedding Date</label>
                        <input type="date" class="form-control" id="weddingDate" name="weddingDate" value="${sessionScope.user.weddingDate}" required>
                    </div>

                    <div class="d-flex gap-2">
                        <button type="submit" class="btn btn-primary">Update Profile</button>
                        <button type="button" class="btn btn-danger" onclick="deleteAccount()">Delete Account</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="col-md-4">
        <div class="card">
            <div class="card-header" style="background-color: var(--accent); border-color: var(--primary);">
                <h5 class="mb-0" style="color: var(--secondary);">Account Info</h5>
            </div>
            <div class="card-body">
                <p><strong>Account Status:</strong> <span class="badge bg-success">Active</span></p>
                <p><strong>Member Since:</strong> <span class="badge bg-info">2024</span></p>
                <p><strong>Wedding Date:</strong> ${sessionScope.user.weddingDate}</p>
            </div>
        </div>

        <div class="card mt-3">
            <div class="card-header" style="background-color: var(--accent); border-color: var(--primary);">
                <h5 class="mb-0" style="color: var(--secondary);">Quick Links</h5>
            </div>
            <div class="card-body">
                <ul style="list-style: none; padding: 0;">
                    <li class="mb-2"><a href="vendor?action=list" class="btn btn-sm btn-outline-primary w-100">Browse Vendors</a></li>
                    <li class="mb-2"><a href="booking?action=list" class="btn btn-sm btn-outline-primary w-100">View Bookings</a></li>
                    <li class="mb-2"><a href="budget?action=list" class="btn btn-sm btn-outline-primary w-100">View Budget</a></li>
                </ul>
            </div>
        </div>
    </div>
</div>

<script>
    function deleteAccount() {
        if (confirm('Are you sure you want to delete your account? This action cannot be undone.')) {
            var form = document.createElement('form');
            form.method = 'POST';
            form.action = 'user';
            
            var actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'action';
            actionInput.value = 'delete';
            form.appendChild(actionInput);
            
            document.body.appendChild(form);
            form.submit();
        }
    }
</script>

<%@ include file="footer.jsp" %>
