/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Producto.DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Producto.modelo.Item;


/**
 *
 * @author ander
 */
public class ProveedorDAO {
    /**
     * Obtiene todos los proveedores de la tabla tb_proveedor.
     * @return Lista de objetos Item (ID y Nombre de Proveedor).
     */
    public List<Item> obtenerTodos() {
        List<Item> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        //  AJUSTA los nombres de tabla y columnas si son diferentes en tu BD.
        String sql = "SELECT idProveedor, nombreProveedor FROM tb_proveedor ORDER BY nombreProveedor"; 

        try {
            con = ConexionBD.getConexion(); // ⬅️ LLAMADA CORREGIDA
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idProveedor");
                String nombre = rs.getString("nombreProveedor");
                lista.add(new Item(id, nombre));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener proveedores: " + e.getMessage());
        } finally {
            // Cierre de recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos de ProveedorDAO: " + e.getMessage());
            }
        }
        return lista;
    }
    
}
