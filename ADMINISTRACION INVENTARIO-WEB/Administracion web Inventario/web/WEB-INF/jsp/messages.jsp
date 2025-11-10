<c:if test="${not empty ok}">
  <div class="alert alert-success" role="alert">${ok}</div>
</c:if>
<c:if test="${not empty error}">
  <div class="alert alert-danger" role="alert">${error}</div>
</c:if>
