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
import Producto.modelo.Producto; 

/**
 *
 * @author ander
 */
public class ProductoDAO {
     // ⚠️ AJUSTA los nombres de tabla y columnas a tu esquema
    private static final String TABLA = "tb_producto";
    
    /**
     * Registra un nuevo producto.
     */
    public boolean registrarProducto(Producto p) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "INSERT INTO " + TABLA + " (nombre, marca, modelo, cantidad, costoUnitario, idCategoria, idProveedor) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getModelo());
            ps.setInt(4, p.getCantidad());
            ps.setDouble(5, p.getCostoUnitario());
            ps.setInt(6, p.getCategoriaId());
            ps.setInt(7, p.getProveedorId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar producto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos de ProductoDAO: " + e.getMessage());
            }
        }
    }

    /**
     * Obtiene la lista completa de productos.
     */
    public List<Producto> obtenerTodosProductos() {
        List<Producto> productos = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql = "SELECT idProducto, nombre, marca, modelo, cantidad, costoUnitario, idCategoria, idProveedor FROM " + TABLA;
        
        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("idProducto"),
                    rs.getString("nombre"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getInt("cantidad"),
                    rs.getDouble("costoUnitario"),
                    rs.getInt("idCategoria"),
                    rs.getInt("idProveedor")
                );
                productos.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return productos;
    }
    
    /**
     * Obtiene productos filtrados por un criterio (ID o parte del Nombre/Marca/Modelo).
     * Este es el método nuevo para la funcionalidad de búsqueda robusta.
     * @param criterio Texto de búsqueda del usuario.
     * @return Lista de productos que coinciden con el criterio.
     */
    public List<Producto> buscarProductosPorCriterio(String criterio) {
        List<Producto> productos = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        // SQL que busca por ID exacto, o por nombre/marca/modelo parcial (ILIKE ignora mayúsculas/minúsculas)
        String sql = "SELECT idProducto, nombre, marca, modelo, cantidad, costoUnitario, idCategoria, idProveedor FROM " + TABLA +
                     " WHERE CAST(idProducto AS TEXT) = ? OR nombre ILIKE ? OR marca ILIKE ? OR modelo ILIKE ?";

        // Preparamos el criterio para la búsqueda parcial con comodines
        String criterioParcial = "%" + criterio.trim() + "%";

        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);
            
            // Parámetro 1: Búsqueda exacta por ID (necesita el criterio original)
            ps.setString(1, criterio.trim()); 
            
            // Parámetros 2, 3 y 4: Búsqueda parcial (LIKE) en Nombre, Marca y Modelo
            ps.setString(2, criterioParcial); 
            ps.setString(3, criterioParcial);
            ps.setString(4, criterioParcial);
            
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("idProducto"),
                    rs.getString("nombre"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getInt("cantidad"),
                    rs.getDouble("costoUnitario"),
                    rs.getInt("idCategoria"),
                    rs.getInt("idProveedor")
                );
                productos.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar productos por criterio: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos de busqueda: " + e.getMessage());
            }
        }
        return productos;
    }
    
    /**
     * Actualiza un producto existente.
     */
    public boolean actualizarProducto(Producto p) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "UPDATE " + TABLA + " SET nombre=?, marca=?, modelo=?, cantidad=?, costoUnitario=?, idCategoria=?, idProveedor=? " +
                      "WHERE idProducto=?";
        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);
            
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getModelo());
            ps.setInt(4, p.getCantidad());
            ps.setDouble(5, p.getCostoUnitario());
            ps.setInt(6, p.getCategoriaId());
            ps.setInt(7, p.getProveedorId());
            ps.setInt(8, p.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
    
    /**
     * Elimina un producto por su ID.
     */
    public boolean eliminarProducto(int idProducto) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "DELETE FROM " + TABLA + " WHERE idProducto = ?";
        try {
            con = ConexionBD.getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProducto);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
}
