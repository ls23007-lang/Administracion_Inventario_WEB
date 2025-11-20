/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reporte.kardex.app.dao;

import Conexion.DB;
import Movimiento.app.modelo.Movimiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dell
 */
public class KardexDAO {
     public List<Movimiento> getKardex() {
        List<Movimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimiento ORDER BY id ASC";

        try (Connection con = DB.getConnection();
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
                lista.add(m);
            }

        } catch (Exception e) {
            System.err.println("Error al listar movimientos: " + e.getMessage());
        }
        return lista;
    }
}
