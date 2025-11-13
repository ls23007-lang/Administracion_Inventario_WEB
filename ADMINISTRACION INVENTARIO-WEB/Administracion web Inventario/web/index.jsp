<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="WEB-INF/jsp/header.jsp" %>

<section class="hero text-center">
  <div class="container">
    <h1 class="display-5 fw-bold mb-3">GESTOR INVENTARIO</h1>
    <p class="lead text-muted mb-5">Gestiona todo tu inventario fácilmente desde un solo panel.</p>

    <div class="d-flex flex-wrap justify-content-center gap-3">
      
      <a class="btn btn-orange-lg" href="categorias">Gestionar Categoría</a>
      <a class="btn btn-orange-lg" href="proveedores">Gestionar Proveedores</a>
      
      <a class="btn btn-orange-lg" href="MovimientoServlet">Gestionar Movimientos</a>
     
    </div>

  </div>
</section>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
