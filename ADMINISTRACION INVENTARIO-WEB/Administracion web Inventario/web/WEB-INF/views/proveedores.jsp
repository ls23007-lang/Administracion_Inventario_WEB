<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../jsp/header.jsp" %>

<div class="container py-4">

  <div class="d-flex align-items-center mb-3">
    <h1 class="h3 mb-0">Proveedores</h1>
    <div class="ms-auto">
  <a class="btn btn-outline-orange me-2" href="${pageContext.request.contextPath}/">Inicio</a>
  <a class="btn btn-outline-orange" href="${pageContext.request.contextPath}/categorias">Categorías</a>
</div>
  </div>

  <c:if test="${not empty ok}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
      ${ok}<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>
  <c:if test="${not empty error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
      ${error}<button type="button" class="btn-close" data-bs-dismiss="alert"></button>
    </div>
  </c:if>

  <div class="card card-dark shadow-sm mb-4">
    <div class="card-body">
      <form action="${pageContext.request.contextPath}/proveedores" method="post" class="row g-3">
        <input type="hidden" name="id" value="${proveedor.id}"/>

        <div class="col-md-4">
          <label class="form-label">Nombre</label>
          <input class="form-control" name="nombre" value="${proveedor.nombre}" placeholder="Ej. TechWorld" required maxlength="100">
        </div>

        <div class="col-md-4">
          <label class="form-label">Teléfono</label>
          <input class="form-control" name="telefono" value="${proveedor.telefono}" placeholder="Ej. 7890-1234" maxlength="20">
        </div>

        <div class="col-md-4">
          <label class="form-label">Código</label>
          <input class="form-control" name="codigo" value="${proveedor.codigo}" placeholder="Ej. SV-001" maxlength="50">
        </div>

        <div class="col-12">
  <button class="btn btn-orange">Guardar</button>
  <a class="btn btn-orange" href="${pageContext.request.contextPath}/proveedores">Nuevo</a>
</div>
      </form>
    </div>
  </div>

  <div class="card card-dark shadow-sm">
    <div class="card-body">
      <div class="table-responsive">
        <table class="table table-dark-inv align-middle">
          <thead>
            <tr>
              <th style="width: 80px;">ID</th>
              <th>Nombre</th>
              <th>Teléfono</th>
              <th>Código</th>
              <th style="width: 220px;">Acciones</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="p" items="${proveedores}">
              <tr>
                <td>${p.id}</td>
                <td>${p.nombre}</td>
                <td>${p.telefono}</td>
                <td>${p.codigo}</td>
                <td>
                  <a class="btn btn-sm btn-gray"
                     href="${pageContext.request.contextPath}/proveedores?action=edit&id=${p.id}">Editar</a>
                  <a class="btn btn-sm btn-orange"
                     href="${pageContext.request.contextPath}/proveedores?action=delete&id=${p.id}"
                     onclick="return confirm('¿Eliminar proveedor?')">Eliminar</a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body></html>
