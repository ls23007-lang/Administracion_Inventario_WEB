<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Iniciar Sesión - Inventario</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #212529; /* Fondo oscuro */
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .login-card {
            width: 100%;
            max-width: 400px;
            padding: 2rem;
            border-radius: 10px;
            background-color: white;
            box-shadow: 0 4px 15px rgba(0,0,0,0.5);
        }
    </style>
</head>
<body>

    <div class="login-card text-center">
        <h2 class="fw-bold mb-4 text-secondary">Bienvenido</h2>
        
        <% if(request.getAttribute("error") != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <form action="LoginServlet" method="POST">
            <div class="form-floating mb-3">
                <input type="text" class="form-control" id="usuario" name="usuario" placeholder="Usuario" required>
                <label for="usuario">Usuario</label>
            </div>
            <div class="form-floating mb-3">
                <input type="password" class="form-control" id="password" name="password" placeholder="Contraseña" required>
                <label for="password">Contraseña</label>
            </div>

            <div class="d-grid gap-2">
                <button type="submit" class="btn btn-primary btn-lg">Ingresar</button>
            </div>
        </form>
        
        <div class="mt-3 text-muted small">
            <p>Credenciales Demo:<br>User: <strong>admin</strong> | Pass: <strong>1234</strong></p>
        </div>
    </div>

</body>
</html>
