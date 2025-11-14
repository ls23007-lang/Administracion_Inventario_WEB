/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Producto.app.dao;
import Producto.app.modelo.Item;
import Conexion.DB; // <-- ¡Asegúrate de que esta clase exista!
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) para Proveedores.
 * Proporciona métodos para interactuar con la tabla 'proveedores'.
 */
public class ProveedorDAO {
    
    // Consulta para obtener el ID y el nombre del proveedor
    private static final String SELECT_ALL_SQL = "SELECT id_proveedor, nombre FROM proveedores ORDER BY nombre";

    /**
     * Obtiene todos los proveedores de la base de datos como objetos Item (ID y nombre).
     * @return Lista de objetos Item.
     */
    public List<Item> obtenerTodos() {
        List<Item> lista = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión a la base de datos
            con = DB.getConnection();
            ps = con.prepareStatement(SELECT_ALL_SQL);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_proveedor");
                String nombre = rs.getString("nombre");
                // Crea un objeto Item con el ID y el nombre del proveedor
                lista.add(new Item(id, nombre));
            }
        } catch (RuntimeException | SQLException e) {
            // Manejar errores de conexión (RuntimeException) o de SQL
            Logger.getLogger(ProveedorDAO.class.getName()).log(Level.SEVERE, "Error al obtener proveedores.", e);
        } finally {
            // Cerrar recursos en el orden inverso de apertura
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                Logger.getLogger(ProveedorDAO.class.getName()).log(Level.WARNING, "Error al cerrar recursos en ProveedorDAO.", e);
            }
        }
        return lista;
    }}