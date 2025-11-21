/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Reporte.kardex.app.web;

import Movimiento.app.dao.MovimientoDAO;
import Movimiento.app.modelo.Movimiento;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.itextpdf.layout.property.VerticalAlignment;
import java.util.List;

@WebServlet(name = "KardexServlet", urlPatterns = {"/KardexServlet"})
public class KardexServlet extends HttpServlet {

    MovimientoDAO movimientoDAO = new MovimientoDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Movimiento> listaMovimientos = movimientoDAO.listarMovimientos();
        
        request.setAttribute("listaMovimientos", listaMovimientos);
        
        request.getSession().setAttribute("listaMovimientosPDF", listaMovimientos);
        
        request.getRequestDispatcher("/WEB-INF/views/kardex.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Movimiento> lista = (List<Movimiento>) request.getSession().getAttribute("listaMovimientosPDF");
        
        if (lista == null) {
            lista = movimientoDAO.listarMovimientos();
        }

        String modo = request.getParameter("modo");
        String disposicion = "inline";
        
        if ("descargar".equals(modo)) {
            disposicion = "attachment";
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", disposicion + "; filename=\"Reporte_Kardex.pdf\"");

        try {
            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            Paragraph title = new Paragraph("Reporte de Kardex")
                    .setFontSize(16f)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20f);
            document.add(title);

            float[] columnWidths = {30f, 60f, 120f, 80f, 40f, 60f, 70f, 40f}; 
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.setWidth(UnitValue.createPercentValue(100));

            String[] headers = {"ID", "Tipo", "Producto", "Marca", "Cant.", "Costo", "Fecha", "Prov."};
            
            for (String header : headers) {
                table.addHeaderCell(new Cell().add(new Paragraph(header).setBold().setFontSize(10f))
                        .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(TextAlignment.CENTER));
            }

            for (Movimiento m : lista) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(m.getId()))).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(m.getTipo())));
                table.addCell(new Cell().add(new Paragraph(m.getModelo())));
                table.addCell(new Cell().add(new Paragraph(m.getMarca())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(m.getCantidad()))).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(String.format("$ %.2f", m.getCosto_unitario()))).setTextAlignment(TextAlignment.RIGHT));
                table.addCell(new Cell().add(new Paragraph(m.getFecha())).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(m.getId_proveedor()))).setTextAlignment(TextAlignment.CENTER));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet Kardex";
    }
}
