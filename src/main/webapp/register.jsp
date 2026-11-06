<%@ include file="header.jsp" %>

<div class="row justify-content-center my-5">
    <div class="col-md-6">
        <div class="card">
            <div class="card-body p-5">
                <h2 class="card-title text-center mb-4" style="color: var(--primary);">Create Account</h2>

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

                <form action="user" method="POST">
                    <input type="hidden" name="action" value="register">

                    <div class="mb-3">
                        <label for="name" class="form-label">Full Name</label>
                        <input type="text" class="form-control" id="name" name="name" required>
                    </div>

                    <div class="mb-3">
                        <label for="email" class="form-label">Email Address</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>

                    <div class="mb-3">
                        <label for="weddingDate" class="form-label">Wedding Date</label>
                        <input type="date" class="form-control" id="weddingDate" name="weddingDate" required>
                    </div>

                    <button type="submit" class="btn btn-primary w-100 py-2">Register</button>
                </form>

                <div class="text-center mt-3">
                    <p>Already have an account? <a href="login.jsp">Login here</a></p>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp" %>
