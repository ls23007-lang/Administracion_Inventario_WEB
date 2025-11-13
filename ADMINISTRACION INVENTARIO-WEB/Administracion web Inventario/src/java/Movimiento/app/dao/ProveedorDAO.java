/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Movimiento.app.dao;

import java.sql.*;
import java.util.*;
import Movimiento.app.modelo.Proveedor;
import Conexion.DB;

/**
 *
 * @author Usuario
 */
public class ProveedorDAO {
    
    public List<Proveedor> listarProveedores() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT id_proveedor, nombre FROM proveedores ORDER BY nombre";

        try (Connection con = DB.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Proveedor p = new Proveedor();
                p.setId_proveedor(rs.getInt("id_proveedor"));
                p.setNombre(rs.getString("nombre"));
                lista.add(p);
            }

        } catch (Exception e) {
            System.err.println("Error al listar proveedores: " + e.getMessage());
        }
        return lista;
    }
}
