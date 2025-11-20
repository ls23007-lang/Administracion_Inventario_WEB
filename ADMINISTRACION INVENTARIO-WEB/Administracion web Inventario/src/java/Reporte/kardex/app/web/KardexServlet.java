/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Reporte.kardex.app.web;

import Movimiento.app.dao.MovimientoDAO;
import Movimiento.app.modelo.Movimiento;
import java.io.IOException;
import java.io.PrintWriter;
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
import java.util.List;
/**
 *
 * @author Dell
 */
@WebServlet(name = "KardexServlet", urlPatterns = {"/KardexServlet"})
public class KardexServlet extends HttpServlet {

        MovimientoDAO movimientoDAO = new MovimientoDAO();

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
            out.println("<title>Servlet KardexServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet KardexServlet at " + request.getContextPath() + "</h1>");
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
 List<Movimiento> listaMovimientos = movimientoDAO.listarMovimientos();
  request.setAttribute("listaMovimientos", listaMovimientos);
  request.getRequestDispatcher("/WEB-INF/views/kardex.jsp").forward(request, response);
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
// Obtener la lista desde request
        List<Movimiento> lista = (List<Movimiento>) request.getAttribute("listaMovimientos");
        if (lista == null) {
            lista = (List<Movimiento>) request.getSession().getAttribute("listaMovimientos");
        }

        //Preparar la respuesta HTTP para PDF
        response.setContentType("inventarioKardex/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"kardex.pdf\"");

        // Crear PDF con iText 7
        PdfWriter writer = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        try {
            // Título
            Paragraph title = new Paragraph("Kardex")
                    .setFontSize(14f)
                    .setBold()
                    .setMarginBottom(8f);
            document.add(title);

            // Tabla con anchos porcentuales
            // Usamos UnitValue para que ocupe 100% ancho de página
            float[] columnWidths = {30f, 60f, 140f, 80f, 40f, 60f, 70f, 60f, 80f};
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.setWidth(UnitValue.createPercentValue(100));

            // Cabeceras (puedes aplicar estilo si quieres)
            table.addHeaderCell(new Cell().add(new Paragraph("ID").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Tipo").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Producto").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Marca").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Cant.").setBold()).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(new Cell().add(new Paragraph("Costo").setBold()).setTextAlignment(TextAlignment.RIGHT));
            table.addHeaderCell(new Cell().add(new Paragraph("Fecha").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Prov.").setBold()).setTextAlignment(TextAlignment.CENTER));

            // Filas
            String ctx = request.getContextPath();
            for (Movimiento m : lista) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(m.getId()))));
                table.addCell(new Cell().add(new Paragraph(m.getTipo())));
                table.addCell(new Cell().add(new Paragraph(m.getModelo())));
                table.addCell(new Cell().add(new Paragraph(m.getMarca())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(m.getCantidad())))
                        .setTextAlignment(TextAlignment.CENTER)
                        .setVerticalAlignment(VerticalAlignment.MIDDLE));
                table.addCell(new Cell().add(new Paragraph(String.format("$ %.2f", m.getCosto_unitario())))
                        .setTextAlignment(TextAlignment.RIGHT));
                table.addCell(new Cell().add(new Paragraph(m.getFecha())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(m.getId_proveedor()))).setTextAlignment(TextAlignment.CENTER));
                
            }

            // Añadir tabla al documento
            document.add(table);

        } finally {
            // Siempre cerrar documento (esto también cierra el OutputStream subyacente)
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
