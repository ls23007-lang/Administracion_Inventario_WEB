<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../jsp/header.jsp" %>
<!DOCTYPE html>
<%
    String ctx = request.getContextPath();
%>

<div class="row">
<div class="col-lg-8">
    <div class="card shadow border-0">
        <div class="card-header bg-white border-bottom">
            <h5 class="mb-0 text-primary">Listado de Productos</h5>
        </div>
        <div class="card-body p-0">
            <table class="table table-striped table-hover align-middle mb-0">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Marca</th>
                        <th>Modelo</th>
                        <th>Costo Unitario</th>
                        <th>Cantidad</th>
                        <th>Categoría</th>
                        <th>Proveedor</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="prod" items="${listaProductos}">
                        <tr>
                            <td>${prod.id}</td>
                            <td>${prod.nombre}</td>
                            <td>${prod.marca}</td>
                            <td>${prod.modelo}</td>
                            <td>$${prod.costoUnitario}</td>
                            <td>${prod.cantidad}</td>
                            <td>${prod.categoria}</td>
                            <td>${prod.proveedor}</td>
                        </tr>
                    </c:forEach>

                    <c:if test="${empty listaProductos}">
                        <tr>
                            <td colspan="9" class="text-center py-4 text-muted">
                                No hay productos registrados aún.
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>

        <div class="card-footer bg-white border-top">
            <!-- Generar PDF (misma pestaña) -->
            <form action="${ctx}/ExistenciasServlet" method="post" style="display:inline;">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-file-earmark-pdf"></i> Generar PDF
                </button>
            </form>

            <!-- Abrir PDF en nueva pestaña -->
            <form action="${ctx}/ExistenciasServlet" method="post" target="_blank" style="display:inline; margin-left:8px;">
                <button type="submit" class="btn btn-outline-secondary">
                    Abrir PDF en nueva pestaña
                </button>
            </form>
        </div>

    </div>
</div>
</div>
 
    </body>
</html>
