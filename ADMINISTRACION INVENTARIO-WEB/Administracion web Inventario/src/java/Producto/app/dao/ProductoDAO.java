/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Producto.app.dao;
import Conexion.DB;
import Producto.app.modelo.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();

        String sql =
            "SELECT p.id_producto AS id, p.nombre, p.marca, p.modelo, " +
            "c.nombre AS categoria, pr.nombre AS proveedor, " +
            "p.cantidad, p.costounitario " +
            "FROM productos p " +
            "LEFT JOIN categorias c ON p.id_categoria = c.id_categoria " +
            "LEFT JOIN proveedores pr ON p.id_proveedor = pr.id_proveedor " +
            "ORDER BY p.id_producto ASC";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id")); // alias de id_producto
                p.setNombre(rs.getString("nombre"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));
                p.setCategoria(rs.getString("categoria"));
                p.setProveedor(rs.getString("proveedor"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setCostoUnitario(rs.getDouble("costounitario")); // corregido aquí
                lista.add(p);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error listando productos", e);
        }

        return lista;
    }

    public boolean insertar(Producto p) {
        String sql =
            "INSERT INTO productos (nombre, marca, modelo, id_categoria, id_proveedor, cantidad, costounitario) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getModelo());
            ps.setInt(4, p.getIdCategoria());
            ps.setInt(5, p.getIdProveedor());
            ps.setInt(6, p.getCantidad());
            ps.setDouble(7, p.getCostoUnitario());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException("Error insertando producto", e);
        }
    }

    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM productos WHERE id_producto = ?";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Producto p = new Producto();
                    p.setId(rs.getInt("id_producto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setMarca(rs.getString("marca"));
                    p.setModelo(rs.getString("modelo"));
                    p.setIdCategoria(rs.getInt("id_categoria"));
                    p.setIdProveedor(rs.getInt("id_proveedor"));
                    p.setCantidad(rs.getInt("cantidad"));
                    p.setCostoUnitario(rs.getDouble("costounitario")); // corregido aquí también
                    return p;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo producto", e);
        }

        return null;
    }

    public boolean actualizar(Producto p) {
        String sql =
            "UPDATE productos SET nombre=?, marca=?, modelo=?, id_categoria=?, id_proveedor=?, cantidad=?, costounitario=? WHERE id_producto=?";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, p.getNombre());
            ps.setString(2, p.getMarca());
            ps.setString(3, p.getModelo());
            ps.setInt(4, p.getIdCategoria());
            ps.setInt(5, p.getIdProveedor());
            ps.setInt(6, p.getCantidad());
            ps.setDouble(7, p.getCostoUnitario());
            ps.setInt(8, p.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException("Error actualizando producto", e);
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            throw new RuntimeException("Error eliminando producto", e);
        }
    }}