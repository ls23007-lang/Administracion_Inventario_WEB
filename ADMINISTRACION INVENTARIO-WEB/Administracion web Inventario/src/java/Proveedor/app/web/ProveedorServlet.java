package Proveedor.app.web;

import Proveedor.app.dao.ProveedorDao;
import Proveedor.app.dao.ProveedorDaoJdbc;
import Proveedor.app.domain.Proveedor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProveedorServlet", urlPatterns = {"/proveedores"})
public class ProveedorServlet extends HttpServlet {

    private final ProveedorDao dao = new ProveedorDaoJdbc();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("edit".equalsIgnoreCase(action)) {
            editar(req, resp);
        } else if ("delete".equalsIgnoreCase(action)) {
            eliminar(req, resp);
        } else {
            listar(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String nombre = req.getParameter("nombre");
        String telefono = req.getParameter("telefono");
        String codigo = req.getParameter("codigo");

        try {
            if (idStr == null || idStr.isEmpty()) {
                dao.insertar(new Proveedor(null, nombre, telefono, codigo));
                req.setAttribute("ok", "Proveedor creado.");
            } else {
                int id = Integer.parseInt(idStr);
                dao.actualizar(new Proveedor(id, nombre, telefono, codigo));
                req.setAttribute("ok", "Proveedor actualizado.");
            }
        } catch (Exception e) {
            req.setAttribute("error", "Error al guardar proveedor");
        }
        listar(req, resp);
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Proveedor> lista = dao.listar();
        req.setAttribute("proveedores", lista);
        // si se viene de editar, podría existir "proveedor"; si no, creamos vacío para el form
        if (req.getAttribute("proveedor") == null) {
            req.setAttribute("proveedor", new Proveedor());
        }
        req.getRequestDispatcher("/WEB-INF/views/proveedores.jsp").forward(req, resp);
    }

    private void editar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Proveedor p = dao.buscarPorId(id);
            if (p == null) {
                req.setAttribute("error", "Proveedor no encontrado");
            } else {
                req.setAttribute("proveedor", p);
            }
        } catch (NumberFormatException ex) {
            req.setAttribute("error", "ID inválido");
        }
        listar(req, resp);
    }

    private void eliminar(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            dao.eliminar(id);
            req.setAttribute("ok", "Proveedor eliminado.");
        } catch (Exception e) {
            req.setAttribute("error", "Error al eliminar proveedor");
        }
        listar(req, resp);
    }
}
