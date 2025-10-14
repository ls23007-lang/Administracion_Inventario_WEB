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
    
    private KardexTableModel kardexTModel = new KardexTableModel();
    
    //Metodo para obtener los datos
       public void getMovimientos() {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM movimiento";

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Movimiento m = new Movimiento();
 
                m.setId(rs.getInt("id"));
                m.setTipo(rs.getString("tipo"));
                m.setId_producto(rs.getInt("id_producto"));
                m.setMarca(rs.getString("marca"));
                m.setModelo(rs.getString("modelo"));
                m.setCantidad(rs.getInt("cantidad"));
                m.setCosto_unitario(rs.getDouble("costo_unitario"));
                m.setFecha(rs.getString("fecha"));
                m.setId_proveedor(rs.getInt("id_proveedor"));
                this.kardexTModel.addMovimiento(m);        

            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
}
