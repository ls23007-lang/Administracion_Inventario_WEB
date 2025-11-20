<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="../jsp/header.jsp" %>
<!DOCTYPE html>

 <div class="container mt-5">
    <div class="row">
      <div class="col-12 text-center mb-4">
        <h2 class="fw-bold text-secondary">Generar Reporte</h2>
        <h3>Seleccione reporte a generar</h3>
      </div>
    </div>

    <div class="row justify-content-center">
      <div class="col-md-6">
        <!-- Form para botón 1 -->
        <form action="${ctx}/ReporteServlet" method="post" class="d-inline-block me-2">
          <input type="hidden" name="accion" value="kardex" />
          <button type="submit" class="btn btn-primary btn-lg">Kardex</button>
        </form>

        <!-- Form para botón 2 -->
        <form action="${ctx}/ReporteServlet" method="post" class="d-inline-block">
          <input type="hidden" name="accion" value="existencias" />
          <button type="submit" class="btn btn-secondary btn-lg">Existencias</button>
        </form>
      </div>
    </div>
  </div>

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
