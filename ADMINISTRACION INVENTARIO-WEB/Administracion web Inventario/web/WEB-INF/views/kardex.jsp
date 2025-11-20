<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 
    Document   : kardex
    Created on : 18 nov 2025, 4:30:44 a. m.
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
    String ctx = request.getContextPath();
%>

<div class="row">
  <div class="col-lg-8">
    <div class="card shadow border-0">
      <div class="card-header bg-white border-bottom">
        <h5 class="mb-0 text-primary">KARDEX</h5>
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
                    <a href="${ctx}/MovimientoServlet?accion=editar&id=${mov.id}" class="btn btn-primary btn-sm" title="Editar">Editar</a>
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

      <div class="card-footer bg-white border-top">
        <!-- Botón para generar PDF (en la misma pestaña) -->
        <form action="${ctx}/KardexServlet" method="post" style="display:inline;">
          <button type="submit" class="btn btn-primary">
            <i class="bi bi-file-earmark-pdf"></i> Generar PDF
          </button>
        </form>

        <!-- Botón para abrir PDF en nueva pestaña -->
        <form action="${ctx}/KardexServlet" method="post" target="_blank" style="display:inline; margin-left:8px;">
          <button type="submit" class="btn btn-outline-secondary">
            Abrir PDF en nueva pestaña
          </button>
        </form>
      </div>

    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
