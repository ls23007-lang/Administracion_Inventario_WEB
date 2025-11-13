<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 
    IMPORTANTE: 
    Usamos "../" para salir de la carpeta 'views' y entrar a 'jsp' para buscar el header.
--%>
<%@ include file="../jsp/header.jsp" %>

<div class="container mt-5">
    
    <div class="row">
        <div class="col-12 text-center mb-4">
            <h2 class="fw-bold text-secondary">Gestionar Movimientos</h2>
        </div>
    </div>

    <div class="row">
        <div class="col-lg-4 mb-4">
            <div class="card shadow border-0">
                <div class="card-header bg-dark text-white">
                    <h5 class="mb-0"><i class="bi bi-pencil-square"></i> Formulario</h5>
                </div>
                <div class="card-body bg-light">
                    
                    <form action="MovimientoServlet" method="POST">
                        
                        <input type="hidden" name="accion" value="${empty movimientoParaEditar ? 'registrar' : 'actualizar'}" />
                        <input type="hidden" name="id" value="${movimientoParaEditar.id}" />

                        <div class="mb-3">
                            <label class="form-label fw-bold">Tipo de Movimiento:</label>
                            <select name="tipo" class="form-select">
                                <option value="Entrada" ${movimientoParaEditar.tipo == 'Entrada' ? 'selected' : ''}>Entrada</option>
                                <option value="Salida" ${movimientoParaEditar.tipo == 'Salida' ? 'selected' : ''}>Salida</option>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Producto:</label>
                            <select name="idProducto" class="form-select">
                                <c:forEach var="prod" items="${listaProductos}">
                                    <option value="${prod.id_producto}" ${prod.id_producto == movimientoParaEditar.id_producto ? 'selected' : ''}>
                                        ${prod.nombre} - ${prod.marca}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Proveedor:</label>
                            <select name="idProveedor" class="form-select">
                                <c:forEach var="prov" items="${listaProveedores}">
                                    <option value="${prov.id_proveedor}" ${prov.id_proveedor == movimientoParaEditar.id_proveedor ? 'selected' : ''}>
                                        ${prov.id_proveedor} - ${prov.nombre}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="row">
                            <div class="col-6 mb-3">
                                <label class="form-label fw-bold">Cantidad:</label>
                                <input type="number" name="cantidad" class="form-control" value="${movimientoParaEditar.cantidad}" required min="1" />
                            </div>
                            <div class="col-6 mb-3">
                                <label class="form-label fw-bold">Costo ($):</label>
                                <input type="number" step="0.01" name="costo" class="form-control" value="${movimientoParaEditar.costo_unitario}" required min="0" />
                            </div>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Fecha:</label>
                            <input type="date" name="fecha" class="form-control" value="${movimientoParaEditar.fecha}" required />
                        </div>

                        <div class="d-grid gap-2">
                            <button type="submit" class="btn btn-success btn-lg">
                                ${empty movimientoParaEditar ? 'Guardar Movimiento' : 'Actualizar Movimiento'}
                            </button>
                            
                            <a href="MovimientoServlet" class="btn btn-secondary">
                                Limpiar / Cancelar
                            </a>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-lg-8">
            <div class="card shadow border-0">
                <div class="card-header bg-white border-bottom">
                    <h5 class="mb-0 text-primary">Historial de Registros</h5>
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
                                    <th>Cant.</th>
                                    <th>Costo</th>
                                    <th>Fecha</th>
                                    <th>Prov.</th>
                                    <th class="text-center">Acciones</th>
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
                                        <td class="fw-bold">${mov.cantidad}</td>
                                        <td>$${mov.costo_unitario}</td>
                                        <td>${mov.fecha}</td>
                                        <td class="text-center fw-bold">${mov.id_proveedor}</td>
                                        <td class="text-center">
                                            <a href="MovimientoServlet?accion=editar&id=${mov.id}" class="btn btn-primary btn-sm" title="Editar">
                                                Editar
                                            </a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                
                                <c:if test="${empty listaMovimientos}">
                                    <tr>
                                        <td colspan="9" class="text-center py-4 text-muted">
                                            No hay movimientos registrados aún.
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

</body>
</html>