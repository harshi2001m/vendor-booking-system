<%@ include file="header.jsp" %>

<c:if test="${empty sessionScope.user}">
    <c:redirect url="login.jsp"/>
</c:if>

<div class="my-5">
    <h2 style="color: var(--secondary); font-weight: 700; margin-bottom: 2rem;">Wedding Budget Planner</h2>

    <c:if test="${not empty error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${error}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        </div>
    </c:if>

    <div class="row mb-4">
        <div class="col-md-8">
            <div class="card">
                <div class="card-body">
                    <button class="btn btn-primary" onclick="showAddExpenseModal()">Add Expense</button>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card" style="background-color: var(--accent); border: none;">
                <div class="card-body text-center">
                    <h5>Total Budget</h5>
                    <h3 style="color: var(--primary); font-weight: 700;">
                        <c:choose>
                            <c:when test="${not empty totalBudget}">
                                $${String.format("%.2f", totalBudget)}
                            </c:when>
                            <c:otherwise>
                                $0.00
                            </c:otherwise>
                        </c:choose>
                    </h3>
                </div>
            </div>
        </div>
    </div>

    <c:if test="${empty budgetItems}">
        <div class="alert alert-info">
            No expenses added yet. <button class="btn btn-sm btn-primary" onclick="showAddExpenseModal()">Add your first expense</button>
        </div>
    </c:if>

    <c:if test="${not empty budgetItems}">
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Category</th>
                        <th>Description</th>
                        <th>Amount</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${budgetItems}">
                        <tr>
                            <td><span class="badge bg-info">${item.category}</span></td>
                            <td>${item.description}</td>
                            <td style="color: var(--primary); font-weight: 700;">$${String.format("%.2f", item.amount)}</td>
                            <td>
                                <button class="btn btn-sm btn-warning" onclick="editExpense('${item.expenseId}', '${item.category}', '${item.amount}', '${item.description}')">Edit</button>
                                <form action="budget" method="POST" style="display: inline;">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="expenseId" value="${item.expenseId}">
                                    <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Delete this expense?')">Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>
</div>

<!-- Add/Edit Expense Modal -->
<div class="modal fade" id="expenseModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalTitle">Add Expense</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <form action="budget" method="POST">
                <input type="hidden" name="action" id="expenseAction" value="add">
                <input type="hidden" name="expenseId" id="expenseId">
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="category" class="form-label">Category</label>
                        <select class="form-select" id="category" name="category" required>
                            <option value="">Select Category</option>
                            <option value="venue">Venue</option>
                            <option value="catering">Catering</option>
                            <option value="photography">Photography</option>
                            <option value="decoration">Decoration</option>
                            <option value="music">Music/DJ</option>
                            <option value="transportation">Transportation</option>
                            <option value="invitation">Invitation</option>
                            <option value="other">Other</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="amount" class="form-label">Amount ($)</label>
                        <input type="number" class="form-control" id="amount" name="amount" step="0.01" required>
                    </div>
                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                    <button type="submit" class="btn btn-primary" id="submitBtn">Add Expense</button>
                </div>
            </form>
        </div>
    </div>
</div>

<script>
    function showAddExpenseModal() {
        document.getElementById('modalTitle').textContent = 'Add Expense';
        document.getElementById('expenseAction').value = 'add';
        document.getElementById('expenseId').value = '';
        document.getElementById('category').value = '';
        document.getElementById('amount').value = '';
        document.getElementById('description').value = '';
        document.getElementById('submitBtn').textContent = 'Add Expense';
        
        var expenseModal = new bootstrap.Modal(document.getElementById('expenseModal'));
        expenseModal.show();
    }

    function editExpense(expenseId, category, amount, description) {
        document.getElementById('modalTitle').textContent = 'Edit Expense';
        document.getElementById('expenseAction').value = 'update';
        document.getElementById('expenseId').value = expenseId;
        document.getElementById('category').value = category;
        document.getElementById('amount').value = amount;
        document.getElementById('description').value = description;
        document.getElementById('submitBtn').textContent = 'Update Expense';
        
        var expenseModal = new bootstrap.Modal(document.getElementById('expenseModal'));
        expenseModal.show();
    }
</script>

<%@ include file="footer.jsp" %>
