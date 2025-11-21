<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../jsp/header.jsp" %>

<div class="container mt-5">
    
    <div class="row">
        <div class="col-12 text-center mb-4">
            <h2 class="fw-bold text-secondary">Reporte de Existencias</h2>
            <p class="text-muted">Inventario actual de productos</p>
        </div>
    </div>

    <div class="row justify-content-center">
        <div class="col-lg-11"> <div class="card shadow border-0">
                <div class="card-header bg-white border-bottom d-flex justify-content-between align-items-center">
                    <h5 class="mb-0 text-primary">Listado de Productos</h5>
                    
                    <div>
                        <form action="${pageContext.request.contextPath}/ExistenciasServlet?modo=descargar" method="post" style="display:inline;">
                            <button type="submit" class="btn btn-danger btn-sm">
                                <i class="bi bi-file-earmark-pdf"></i> Descargar PDF
                            </button>
                        </form>
                        
                        <form action="${pageContext.request.contextPath}/ExistenciasServlet" method="post" target="_blank" style="display:inline; margin-left: 5px;">
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
                                    <th>Nombre</th>
                                    <th>Marca</th>
                                    <th>Modelo</th>
                                    <th class="text-end">Costo Unit.</th>
                                    <th class="text-center">Cant.</th>
                                    <th>Categoría</th>
                                    <th>Proveedor</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="prod" items="${listaProductos}">
                                    <tr>
                                        <td>${prod.id}</td> <td class="fw-bold text-primary">${prod.nombre}</td>
                                        <td>${prod.marca}</td>
                                        <td>${prod.modelo}</td>
                                        <td class="text-end">$${prod.costoUnitario}</td>
                                        <td class="text-center">
                                            <span class="badge ${prod.cantidad < 5 ? 'bg-warning text-dark' : 'bg-success'}">
                                                ${prod.cantidad}
                                            </span>
                                        </td>
                                        <td>${prod.categoria}</td>
                                        <td>${prod.proveedor}</td>
                                    </tr>
                                </c:forEach>

                                <c:if test="${empty listaProductos}">
                                    <tr>
                                        <td colspan="8" class="text-center py-4 text-muted">
                                            No hay productos registrados.
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