<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../jsp/header.jsp" %>

<div class="container py-4">

  <div class="d-flex align-items-center mb-3">
    <h1 class="h3 mb-0">Categorías</h1>
    <!-- Barra de acciones (esquina derecha) -->
<div class="ms-auto">
  <a class="btn btn-outline-orange me-2" href="${pageContext.request.contextPath}/">Inicio</a>
  <a class="btn btn-outline-orange" href="${pageContext.request.contextPath}/proveedores">Proveedores</a>
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
      <form action="${pageContext.request.contextPath}/categorias" method="post" class="row g-3">
        <input type="hidden" name="action" value="save">
        <input type="hidden" name="id" value="${form.id}"/>

        <div class="col-md-4">
          <label class="form-label">Nombre</label>
          <input class="form-control" name="nombre" value="${form.nombre}" placeholder="Ej. Almacenamiento" required maxlength="80">
        </div>

        <div class="col-md-8">
          <label class="form-label">Descripción</label>
          <input class="form-control" name="descripcion" value="${form.descripcion}" placeholder="Breve descripción" maxlength="250">
        </div>

        <div class="col-12">
  <button class="btn btn-orange">Guardar</button>
  <a class="btn btn-orange" href="${pageContext.request.contextPath}/categorias">Nuevo</a>
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
              <th>Descripción</th>
              <th style="width: 220px;">Acciones</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="c" items="${lista}">
              <tr>
                <td>${c.id}</td>
                <td>${c.nombre}</td>
                <td>${c.descripcion}</td>
                <td>
                  <a class="btn btn-sm btn-gray"
                     href="${pageContext.request.contextPath}/categorias?action=edit&id=${c.id}">Editar</a>
                  <a class="btn btn-sm btn-orange"
                     href="${pageContext.request.contextPath}/categorias?action=delete&id=${c.id}"
                     onclick="return confirm('¿Eliminar categoría?')">Eliminar</a>
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
