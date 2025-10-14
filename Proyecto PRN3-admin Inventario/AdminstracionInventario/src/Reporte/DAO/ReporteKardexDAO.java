/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reporte.DAO;

import Conexiondb.Conexion;
import Movimiento.modelo.Movimiento;
import Reporte.tmodel.KardexTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Dell
 */
public class ReporteKardexDAO {

    // Método para obtener los movimientos desde la BD y agregarlos al modelo del JTable
    public void getMovimientos(KardexTableModel model) {
        String sql = "SELECT * FROM movimiento";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Movimiento m = new Movimiento();
                m.setId(rs.getInt("id"));
                m.setTipo(rs.getString("tipo"));
                m.setId_producto(rs.getInt("id_producto"));
                m.setMarca(rs.getString("marca"));
                m.setModelo(rs.getString("modelo"));
                m.setCantidad(rs.getInt("cantidad"));
                m.setCosto_unitario(rs.getDouble("costo_unitario"));
                m.setFecha(rs.getDate("fecha").toString());
                m.setId_proveedor(rs.getInt("id_proveedor"));

                model.addMovimiento(m);
            }

            // Notificar al JTable que los datos cambiaron
            model.fireTableDataChanged();

        } catch (SQLException e) {
            System.err.println("Error al obtener movimientos: " + e.getMessage());
        }
    }
}
