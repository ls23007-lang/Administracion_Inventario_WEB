<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%
    if (session.getAttribute("usuarioLogueado") == null) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return; 
    }
%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="uri" value="${pageContext.request.requestURI}" />

<!doctype html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Inventario</title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
  <link rel="stylesheet" href="${ctx}/resources/css/app.css">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand fw-semibold" href="${ctx}/">Inventario</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNav">
      <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="mainNav">
      <ul class="navbar-nav ms-auto align-items-center"> <li class="nav-item">
          <a class="nav-link ${fn:contains(uri, '/categorias') ? 'active' : ''}"
             href="${ctx}/categorias">Categorías</a>
        </li>
        <li class="nav-item">
          <a class="nav-link ${fn:contains(uri, '/proveedores') ? 'active' : ''}"
             href="${ctx}/proveedores">Proveedores</a>
        </li>        
        <li class="nav-item">
          <a class="nav-link ${fn:contains(uri, '/MovimientoServlet') ? 'active' : ''}"
             href="${ctx}/MovimientoServlet">Movimientos</a>
        </li>
        <li class="nav-item">
          <a class="nav-link ${fn:contains(uri, '/ProductoServlet') ? 'active' : ''}"
             href="${ctx}/ProductoServlet">Productos</a>
        </li>
        <li class="nav-item">
            <a class="nav-link ${fn:contains(uri, '/ReporteServlet') ? 'active' : ''}"
               href="${ctx}/ReporteServlet">Reportes</a>
        </li>

        <li class="nav-item ms-2 me-2 d-none d-lg-block text-white-50">|</li>

        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle text-white" href="#" role="button" data-bs-toggle="dropdown">
                <i class="bi bi-person-circle"></i> ${sessionScope.usuarioLogueado}
            </a>
            <ul class="dropdown-menu dropdown-menu-end">
                <li>
                    <a class="dropdown-item text-danger" href="${ctx}/LogoutServlet">
                        <i class="bi bi-box-arrow-right"></i> Cerrar Sesión
                    </a>
                </li>
            </ul>
        </li>

      </ul>
    </div>
  </div>
</nav>
