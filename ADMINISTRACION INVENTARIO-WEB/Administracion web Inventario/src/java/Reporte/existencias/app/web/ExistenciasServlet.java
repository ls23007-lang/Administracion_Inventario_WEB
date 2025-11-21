/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Reporte.existencias.app.web;

import Producto.app.dao.ProductoDAO;
import Producto.app.modelo.Producto;
import java.io.IOException;
import java.util.List;
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


@WebServlet(name = "ExistenciasServlet", urlPatterns = {"/ExistenciasServlet"})
public class ExistenciasServlet extends HttpServlet {

    ProductoDAO productoDAO = new ProductoDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Producto> listaProductos = productoDAO.listar();
        
        request.setAttribute("listaProductos", listaProductos);
        
        request.getSession().setAttribute("listaExistenciasPDF", listaProductos);
        
        request.getRequestDispatcher("/WEB-INF/views/existencias.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        List<Producto> lista = (List<Producto>) request.getSession().getAttribute("listaExistenciasPDF");
        
        if (lista == null) {
            lista = productoDAO.listar();
        }

        String modo = request.getParameter("modo");
        String disposicion = "inline"; 
        
        if ("descargar".equals(modo)) {
            disposicion = "attachment"; 
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", disposicion + "; filename=\"Reporte_Existencias.pdf\"");

        try {
            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            Paragraph title = new Paragraph("Listado de Existencias")
                    .setFontSize(16f)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginBottom(20f);
            document.add(title);

            float[] columnWidths = {30f, 100f, 80f, 80f, 60f, 50f, 90f, 90f};
            Table table = new Table(UnitValue.createPercentArray(columnWidths));
            table.setWidth(UnitValue.createPercentValue(100));

            String[] headers = {"ID", "Nombre", "Marca", "Modelo", "Costo", "Cant.", "Categoría", "Proveedor"};
            
            for (String header : headers) {
                table.addHeaderCell(new Cell().add(new Paragraph(header).setBold().setFontSize(10f))
                        .setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY)
                        .setTextAlignment(TextAlignment.CENTER));
            }

            for (Producto p : lista) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(p.getId()))).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(p.getNombre())));
                table.addCell(new Cell().add(new Paragraph(p.getMarca())));
                table.addCell(new Cell().add(new Paragraph(p.getModelo())));
                table.addCell(new Cell().add(new Paragraph(String.format("$ %.2f", p.getCostoUnitario()))).setTextAlignment(TextAlignment.RIGHT));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(p.getCantidad()))).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(p.getCategoria())));
                table.addCell(new Cell().add(new Paragraph(p.getProveedor())));
            }

            document.add(table);
            document.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet Existencias";
    }
}
