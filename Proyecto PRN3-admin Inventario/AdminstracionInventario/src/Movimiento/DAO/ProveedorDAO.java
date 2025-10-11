/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Movimiento.DAO;

import java.sql.*;
import java.util.*;
//import modelo.Conexion;
import Movimiento.modelo.Proveedor;
/**
 *
 * @author Usuario
 */
public class ProveedorDAO {
    
    //Conexion conexion = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Proveedor> listarProveedores() {
        List<Proveedor> lista = new ArrayList<>();
        // Cambiamos "id" por "id_proveedor"
        String sql = "SELECT id_proveedor, nombre FROM proveedores ORDER BY nombre";
        try {
            //con = conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Proveedor p = new Proveedor();
                // Cambiamos getInt("id") por getInt("id_proveedor")
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
