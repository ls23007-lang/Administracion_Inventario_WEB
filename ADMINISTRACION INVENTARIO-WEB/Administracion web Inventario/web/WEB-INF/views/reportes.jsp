<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../jsp/header.jsp" %>
<!DOCTYPE html>

<div class="container mt-5">
    <div class="row">
        <div class="col-12 text-center mb-4">
            <h2 class="fw-bold text-secondary">Generar Reporte</h2>
            <h3 class="text-muted fs-5">Seleccione el reporte a visualizar</h3>
        </div>
    </div>

    <div class="row justify-content-center">
        <div class="col-md-8 text-center">
            
            <a href="${pageContext.request.contextPath}/KardexServlet" class="btn btn-primary btn-lg py-3 px-4 me-3 shadow">
                <i class="bi bi-table"></i> Ver Kardex
            </a>

            <a href="${pageContext.request.contextPath}/ExistenciasServlet" class="btn btn-secondary btn-lg py-3 px-4 shadow">
                <i class="bi bi-box-seam"></i> Ver Existencias
            </a>
            
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

</body>
</html>
