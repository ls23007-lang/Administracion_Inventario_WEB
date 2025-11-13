/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Movimiento.app.controlador;

import Movimiento.app.dao.MovimientoDAO;
import Movimiento.app.dao.ProductoDAO;
import Movimiento.app.dao.ProveedorDAO;
import Movimiento.app.modelo.Movimiento;
import Movimiento.app.modelo.Producto;
import Movimiento.app.modelo.Proveedor;

import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Usuario
 */
@WebServlet(name = "MovimientoServlet", urlPatterns = {"/MovimientoServlet"})
public class MovimientoServlet extends HttpServlet {
    
    MovimientoDAO movimientoDAO = new MovimientoDAO();
    ProductoDAO productoDAO = new ProductoDAO();
    ProveedorDAO proveedorDAO = new ProveedorDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MovimientoServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MovimientoServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        if (accion != null && accion.equals("editar")) {
            String idStr = request.getParameter("id");
            if(idStr != null && !idStr.isEmpty()){
                int id = Integer.parseInt(idStr);
                Movimiento movimientoParaEditar = movimientoDAO.obtenerMovimientoPorId(id);
                request.setAttribute("movimientoParaEditar", movimientoParaEditar);
            }
        }
        
        List<Movimiento> listaMovimientos = movimientoDAO.listarMovimientos();
        List<Producto> listaProductos = productoDAO.listarProductos();
        List<Proveedor> listaProveedores = proveedorDAO.listarProveedores();

        request.setAttribute("listaMovimientos", listaMovimientos);
        request.setAttribute("listaProductos", listaProductos);
        request.setAttribute("listaProveedores", listaProveedores);

        request.getRequestDispatcher("/WEB-INF/views/movimientos.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String accion = request.getParameter("accion");
        
        try {
            String tipo = request.getParameter("tipo");
            int idProducto = Integer.parseInt(request.getParameter("idProducto"));
            int idProveedor = Integer.parseInt(request.getParameter("idProveedor"));
            
            String cantStr = request.getParameter("cantidad");
            String costoStr = request.getParameter("costo");
            
            int cantidad = (cantStr != null && !cantStr.isEmpty()) ? Integer.parseInt(cantStr) : 0;
            double costo = (costoStr != null && !costoStr.isEmpty()) ? Double.parseDouble(costoStr) : 0.0;
            
            String fecha = request.getParameter("fecha");

            Producto p = productoDAO.obtenerProductoPorId(idProducto);

            Movimiento m = new Movimiento(tipo, idProducto, p.getMarca(), p.getModelo(), cantidad, costo, fecha, idProveedor);

            if (accion != null && accion.equals("actualizar")) {
                int id = Integer.parseInt(request.getParameter("id"));
                m.setId(id);
                movimientoDAO.actualizarMovimiento(m);
            } else {
                movimientoDAO.registrarMovimiento(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        response.sendRedirect("MovimientoServlet");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
