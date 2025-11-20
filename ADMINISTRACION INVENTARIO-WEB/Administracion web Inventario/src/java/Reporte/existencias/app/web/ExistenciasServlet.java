/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Reporte.existencias.app.web;

import Producto.app.dao.ProductoDAO;
import Producto.app.modelo.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Link;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
/**
 *
 * @author Dell
 */
@WebServlet(name = "ExistenciasServlet", urlPatterns = {"/ExistenciasServlet"})
public class ExistenciasServlet extends HttpServlet {

            ProductoDAO productoDAO = new ProductoDAO();
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
            out.println("<title>Servlet ExistenciasServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ExistenciasServlet at " + request.getContextPath() + "</h1>");
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
List<Producto> listaMovimientos = productoDAO.listar();
request.setAttribute("listaProductos", productoDAO.listar());
request.getRequestDispatcher("/WEB-INF/views/existencias.jsp").forward(request, response);
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
        List<Producto> lista = (List<Producto>) request.getAttribute("listaProductos");
        if (lista == null) {
            lista = (List<Producto>) request.getSession().getAttribute("listaProductos");
        }

        response.setContentType("inventarioExistencias/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"Existencias.pdf\"");

        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        try {
            Paragraph title = new Paragraph("Listado de Productos")
                    .setFontSize(14f)
                    .setBold()
                    .setMarginBottom(8f);
            document.add(title);

            // Column widths relativas (ajusta si quieres)
            float[] columnWidths = {30f, 150f, 90f, 90f, 80f, 50f, 100f, 100f, 90f};
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.setWidth(UnitValue.createPercentValue(100));

            // Cabeceras
            table.addHeaderCell(new Cell().add(new Paragraph("ID").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Nombre").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Marca").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Modelo").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Costo Unitario").setBold()).setTextAlignment(TextAlignment.RIGHT));
            table.addHeaderCell(new Cell().add(new Paragraph("Cantidad").setBold()).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("Categoría").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Proveedor").setBold()));

            // Construir URL base (para hipervínculos absolutos)
            String ctx = request.getContextPath();
            String scheme = request.getScheme();
            String server = request.getServerName();
            int port = request.getServerPort();
            String portPart = (port == 80 || port == 443) ? "" : ":" + port;
            String baseUrl = scheme + "://" + server + portPart + ctx;

            for (Producto p : lista) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(p.getId()))));
                table.addCell(new Cell().add(new Paragraph(p.getNombre())));
                table.addCell(new Cell().add(new Paragraph(p.getMarca())));
                table.addCell(new Cell().add(new Paragraph(p.getModelo())));
                table.addCell(new Cell().add(new Paragraph(String.format("$ %.2f", p.getCostoUnitario())))
                        .setTextAlignment(TextAlignment.RIGHT));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(p.getCantidad())))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE));
                table.addCell(new Cell().add(new Paragraph(p.getCategoria())));
                table.addCell(new Cell().add(new Paragraph(p.getProveedor())));
            }

            document.add(table);
        } finally {
            document.close();
        }
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
