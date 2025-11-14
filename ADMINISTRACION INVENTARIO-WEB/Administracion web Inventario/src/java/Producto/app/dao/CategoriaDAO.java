/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Producto.app.dao;
import Producto.app.modelo.Item;
import Conexion.DB; // <-- ¡Corregido! Apunta a la clase DB en el paquete Conexion
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) para Categorías.
 * Proporciona métodos para interactuar con la tabla 'categorias'.
 */
public class CategoriaDAO {
    
    private static final String SELECT_ALL_SQL = "SELECT id_categoria, descripcion FROM categorias ORDER BY descripcion";
    
    /**
     * Obtiene todas las categorías de la base de datos como objetos Item (ID y nombre/descripción).
     * @return Lista de objetos Item.
     */
    public List<Item> obtenerTodos() {
        List<Item> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Usar la conexión centralizada (Conexion.DB)
            con = DB.getConnection();
            ps = con.prepareStatement(SELECT_ALL_SQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_categoria");
                String descripcion = rs.getString("descripcion");
                lista.add(new Item(id, descripcion));
            }
        } catch (RuntimeException | SQLException e) {
            // Manejar errores de conexión (RuntimeException) o de SQL
            Logger.getLogger(CategoriaDAO.class.getName()).log(Level.SEVERE, "Error al obtener categorías.", e);
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Logger.getLogger(CategoriaDAO.class.getName()).log(Level.WARNING, "Error al cerrar recursos en CategoriaDAO.", e);
            }
        }
        return lista;
    }
}
