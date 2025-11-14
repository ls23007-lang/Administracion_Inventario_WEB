/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Producto.app.controlador;

import Producto.app.dao.ProductoDAO;
import Producto.app.dao.CategoriaDAO;
import Producto.app.dao.ProveedorDAO;
import Producto.app.modelo.Producto;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(name = "ProductoServlet", urlPatterns = {"/ProductoServlet"})
public class ProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductoDAO productoDAO = new ProductoDAO();
    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    private ProveedorDAO proveedorDAO = new ProveedorDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if (accion == null) accion = "listar";

        try {
            switch (accion) {
                case "listar":
                    request.setAttribute("listaProductos", productoDAO.listar());
                    request.setAttribute("listaCategorias", categoriaDAO.obtenerTodos());
                    request.setAttribute("listaProveedores", proveedorDAO.obtenerTodos());
                    break;

                case "editar":
                    int idEditar = Integer.parseInt(request.getParameter("id"));
                    request.setAttribute("productoParaEditar", productoDAO.obtenerPorId(idEditar));
                    request.setAttribute("listaCategorias", categoriaDAO.obtenerTodos());
                    request.setAttribute("listaProveedores", proveedorDAO.obtenerTodos());
                    break;

                case "eliminar":
                    int idEliminar = Integer.parseInt(request.getParameter("id"));
                    if (productoDAO.eliminar(idEliminar)) {
                        request.setAttribute("mensajeExito", "Producto eliminado correctamente.");
                    } else {
                        request.setAttribute("mensajeError", "No se pudo eliminar el producto.");
                    }
                    request.setAttribute("listaProductos", productoDAO.listar());
                    request.setAttribute("listaCategorias", categoriaDAO.obtenerTodos());
                    request.setAttribute("listaProveedores", proveedorDAO.obtenerTodos());
                    break;
            }
        } catch (Exception e) {
            request.setAttribute("mensajeError", "Error al procesar la acción: " + e.getMessage());
        }

        request.getRequestDispatcher("/WEB-INF/views/productos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        try {
            if ("registrar".equals(accion)) {
                Producto nuevo = new Producto();
                nuevo.setNombre(request.getParameter("nombre"));
                nuevo.setMarca(request.getParameter("marca"));
                nuevo.setModelo(request.getParameter("modelo"));
                nuevo.setCostoUnitario(Double.parseDouble(request.getParameter("costoUnitario")));
                nuevo.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                nuevo.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
                nuevo.setIdProveedor(Integer.parseInt(request.getParameter("idProveedor")));

                if (productoDAO.insertar(nuevo)) {
                    request.setAttribute("mensajeExito", "Producto registrado correctamente.");
                } else {
                    request.setAttribute("mensajeError", "No se pudo registrar el producto.");
                }

            } else if ("actualizar".equals(accion)) {
                Producto actualizado = new Producto();
                actualizado.setId(Integer.parseInt(request.getParameter("id")));
                actualizado.setNombre(request.getParameter("nombre"));
                actualizado.setMarca(request.getParameter("marca"));
                actualizado.setModelo(request.getParameter("modelo"));
                actualizado.setCostoUnitario(Double.parseDouble(request.getParameter("costoUnitario")));
                actualizado.setCantidad(Integer.parseInt(request.getParameter("cantidad")));
                actualizado.setIdCategoria(Integer.parseInt(request.getParameter("idCategoria")));
                actualizado.setIdProveedor(Integer.parseInt(request.getParameter("idProveedor")));

                if (productoDAO.actualizar(actualizado)) {
                    request.setAttribute("mensajeExito", "Producto actualizado correctamente.");
                } else {
                    request.setAttribute("mensajeError", "No se pudo actualizar el producto.");
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("mensajeError", "Formato inválido en cantidad o costo unitario.");
        } catch (Exception e) {
            request.setAttribute("mensajeError", "Ocurrió un error inesperado: " + e.getMessage());
        }

        // Recargar listas siempre
        request.setAttribute("listaProductos", productoDAO.listar());
        request.setAttribute("listaCategorias", categoriaDAO.obtenerTodos());
        request.setAttribute("listaProveedores", proveedorDAO.obtenerTodos());

        request.getRequestDispatcher("/WEB-INF/views/productos.jsp").forward(request, response);
    }
}
