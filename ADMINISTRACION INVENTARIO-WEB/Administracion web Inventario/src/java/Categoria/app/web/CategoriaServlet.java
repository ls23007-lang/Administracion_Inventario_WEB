package Categoria.app.web;

import Categoria.app.dao.CategoriaDao;
import Categoria.app.dao.CategoriaDaoJdbc;
import Categoria.app.domain.Categoria;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoriaServlet", urlPatterns = {"/categorias"})
public class CategoriaServlet extends HttpServlet {

    // Mantén el tipo de la variable como la INTERFAZ:
    private final CategoriaDao dao = new CategoriaDaoJdbc(); // requiere ctor vacío

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("save".equalsIgnoreCase(action)) {
            guardar(req, resp);
        } else {
            listar(req, resp);
        }
    }

    private void listar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Categoria> lista = dao.listar();
        req.setAttribute("lista", lista);
        req.setAttribute("form", new Categoria());
        req.getRequestDispatcher("/WEB-INF/views/categorias.jsp").forward(req, resp);
    }

    private void editar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Categoria c = dao.buscarPorId(id);
            if (c == null) { req.setAttribute("error", "Categoría no encontrada"); listar(req, resp); return; }
            req.setAttribute("lista", dao.listar());
            req.setAttribute("form", c);
            req.getRequestDispatcher("/WEB-INF/views/categorias.jsp").forward(req, resp);
        } catch (NumberFormatException ex) {
            req.setAttribute("error", "ID inválido");
            listar(req, resp);
        }
    }

    private void eliminar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try { int id = Integer.parseInt(req.getParameter("id")); dao.eliminar(id); req.setAttribute("ok","Categoría eliminada."); }
        catch (Exception ex) { req.setAttribute("error", ex.getMessage()); }
        listar(req, resp);
    }

    private void guardar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        try {
            if (idStr == null || idStr.isEmpty()) {
                dao.insertar(new Categoria(null, nombre, descripcion));
                req.setAttribute("ok","Categoría creada.");
            } else {
                int id = Integer.parseInt(idStr);
                dao.actualizar(new Categoria(id, nombre, descripcion));
                req.setAttribute("ok","Categoría actualizada.");
            }
        } catch (Exception ex) {
            req.setAttribute("error", ex.getMessage());
        }
        listar(req, resp);
    }
}
