/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reporte.DAO;

import Conexiondb.Conexion;
import Producto.modelo.Producto;
import Reporte.tmodel.ExistenciaTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Dell
 */
public class ReporteExistenciasDAO {

    // Método para obtener los productos y agregarlos al modelo de la tabla
    public void getProductos(ExistenciaTableModel model) {
        String sql = "SELECT id_producto, nombre, marca, modelo, id_categoria, id_proveedor, costoUnitario, cantidad FROM productos";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));  
                p.setCategoriaId(rs.getInt("id_categoria"));
                p.setProveedorId(rs.getInt("id_proveedor"));
                p.setCostoUnitario(rs.getDouble("costoUnitario"));
                p.setCantidad(rs.getInt("cantidad"));

                model.addProducto(p);
            }

            // Notificar a la tabla que los datos cambiaron
            model.fireTableDataChanged();

        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        }
    }
}
