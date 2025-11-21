<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Incluimos el header para mantener el menú y estilos --%>
<%@ include file="../jsp/header.jsp" %>

<div class="container mt-5">
    
    <div class="row">
        <div class="col-12 text-center mb-4">
            <h2 class="fw-bold text-secondary">Reporte de Kardex</h2>
            <p class="text-muted">Historial completo de movimientos</p>
        </div>
    </div>

    <div class="row justify-content-center">
        <div class="col-lg-10">
            <div class="card shadow border-0">
                <div class="card-header bg-white border-bottom d-flex justify-content-between align-items-center">
                    <h5 class="mb-0 text-primary">Movimientos Registrados</h5>
                    
                    <div>
                        <form action="${pageContext.request.contextPath}/KardexServlet?modo=descargar" method="post" style="display:inline;">
                            <button type="submit" class="btn btn-danger btn-sm">
                                <i class="bi bi-file-earmark-pdf"></i> Descargar PDF
                            </button>
                        </form>
                        
                        <form action="${pageContext.request.contextPath}/KardexServlet" method="post" target="_blank" style="display:inline; margin-left: 5px;">
                            <button type="submit" class="btn btn-outline-danger btn-sm">
                                <i class="bi bi-eye"></i> Ver PDF
                            </button>
                        </form>
                    </div>
                </div>

                <div class="card-body p-0">
                    <div class="table-responsive">
                        <table class="table table-striped table-hover align-middle mb-0">
                            <thead class="table-dark">
                                <tr>
                                    <th>ID</th>
                                    <th>Tipo</th>
                                    <th>Producto</th>
                                    <th>Marca</th>
                                    <th class="text-center">Cant.</th>
                                    <th class="text-end">Costo</th>
                                    <th>Fecha</th>
                                    <th class="text-center">Prov.</th>
                                    </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="mov" items="${listaMovimientos}">
                                    <tr>
                                        <td>${mov.id}</td>
                                        <td>
                                            <span class="badge rounded-pill ${mov.tipo == 'Entrada' ? 'bg-success' : 'bg-danger'}">
                                                ${mov.tipo}
                                            </span>
                                        </td>
                                        <td>${mov.modelo}</td>
                                        <td>${mov.marca}</td>
                                        <td class="text-center fw-bold">${mov.cantidad}</td>
                                        <td class="text-end">$${mov.costo_unitario}</td>
                                        <td>${mov.fecha}</td>
                                        <td class="text-center fw-bold">${mov.id_proveedor}</td>
                                    </tr>
                                </c:forEach>
                                
                                <c:if test="${empty listaMovimientos}">
                                    <tr>
                                        <td colspan="8" class="text-center py-4 text-muted">
                                            No hay movimientos para mostrar.
                                        </td>
                                    </tr>
                                </c:if>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
</body>
</html>
