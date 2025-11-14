<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../jsp/header.jsp" %>

<div class="container mt-5">
    <div class="row">
        <div class="col-12 text-center mb-4">
            <h2 class="fw-bold text-secondary">Gestionar Productos</h2>
        </div>
    </div>

    <!-- Mensajes -->
    <c:if test="${not empty mensajeExito}">
        <div class="alert alert-success">${mensajeExito}</div>
    </c:if>
    <c:if test="${not empty mensajeError}">
        <div class="alert alert-danger">${mensajeError}</div>
    </c:if>

    <div class="row">
        <!-- Formulario -->
        <div class="col-lg-4 mb-4">
            <div class="card shadow border-0">
                <div class="card-header bg-dark text-white">
                    <h5 class="mb-0">Formulario de Producto</h5>
                </div>
                <div class="card-body bg-light">
                    <form action="ProductoServlet" method="POST">
                        <input type="hidden" name="accion" value="${empty productoParaEditar ? 'registrar' : 'actualizar'}" />
                        <input type="hidden" name="id" value="${productoParaEditar.id}" />

                        <div class="mb-3">
                            <label class="form-label fw-bold">Nombre:</label>
                            <input type="text" name="nombre" class="form-control" value="${productoParaEditar.nombre}" required />
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Marca:</label>
                            <input type="text" name="marca" class="form-control" value="${productoParaEditar.marca}" required />
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Modelo:</label>
                            <input type="text" name="modelo" class="form-control" value="${productoParaEditar.modelo}" required />
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Costo Unitario ($):</label>
                            <input type="number" step="0.01" name="costoUnitario" class="form-control" value="${productoParaEditar.costoUnitario}" required />
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Cantidad:</label>
                            <input type="number" name="cantidad" class="form-control" value="${productoParaEditar.cantidad}" required />
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Categoría:</label>
                            <select name="idCategoria" class="form-select" required>
                                <option value="" disabled selected>Seleccione una Categoría</option>
                                <c:forEach var="cat" items="${listaCategorias}">
                                    <option value="${cat.id}" ${cat.id == productoParaEditar.idCategoria ? 'selected' : ''}>
                                        ${cat.nombre}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="mb-3">
                            <label class="form-label fw-bold">Proveedor:</label>
                            <select name="idProveedor" class="form-select" required>
                                <option value="" disabled selected>Seleccione un Proveedor</option>
                                <c:forEach var="prov" items="${listaProveedores}">
                                    <option value="${prov.id}" ${prov.id == productoParaEditar.idProveedor ? 'selected' : ''}>
                                        ${prov.nombre}
                                    </option>
                                </c:forEach>
                            </select>
                        </div>

                        <div class="d-grid gap-2 mt-4">
                            <button type="submit" class="btn btn-success">
                                ${empty productoParaEditar ? 'Guardar Producto' : 'Actualizar Producto'}
                            </button>
                            <a href="ProductoServlet" class="btn btn-secondary">Cancelar</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <!-- Tabla -->
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
                                <th class="text-center">Acciones</th>
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
                                    <td class="text-center">
                                        <a href="ProductoServlet?accion=editar&id=${prod.id}" class="btn btn-primary btn-sm">Editar</a>
                                        <a href="ProductoServlet?accion=eliminar&id=${prod.id}" class="btn btn-danger btn-sm"
                                           onclick="return confirm('¿Está seguro de eliminar el producto: ${prod.nombre}?');">
                                           Eliminar
                                        </a>
                                    </td>
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
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
