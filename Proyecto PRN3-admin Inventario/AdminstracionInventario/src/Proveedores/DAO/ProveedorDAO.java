
package Proveedores.DAO;

import Proveedores.modelo.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {
   public List<Proveedor> listar() {
        String sql = "SELECT id, nombre, telefono, codigo FROM proveedores ORDER BY id";
        List<Proveedor> data = new ArrayList<>();
        try (Connection cn = ConexionBDProv.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                data.add(new Proveedor(
                        
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                 rs.getString("codigo")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar Proveedores: " + e.getMessage(), e);
        }
        return data;
    }

    public Proveedor insertar(Proveedor p) {
        validar(p);
        String sql = "INSERT INTO proveedor(nombre, telefono, codigo) VALUES(?, ?, ?) RETURNING id";
        try (Connection cn = ConexionBDProv.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre().trim());
            ps.setString(2, trimOrNull(p.getTelefono()));
            ps.setString(3, p.getCodigo().trim());
            try (ResultSet rs = ps.executeQuery()) { if (rs.next()) p.setId(rs.getInt(1)); }
            return p;
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState()))
                throw new RuntimeException("Código ya existe.");
            throw new RuntimeException("Error al insertar proveedor: " + e.getMessage(), e);
        }
    }

    public void actualizar(Proveedor p) {
        if (p.getId() == null) throw new IllegalArgumentException("ID requerido para actualizar.");
        validar(p);
        String sql = "UPDATE proveedor SET nombre=?, telefono=?, codigo=? WHERE id=?";
        try (Connection cn = ConexionBDProv.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre().trim());
            ps.setString(2, trimOrNull(p.getTelefono()));
            ps.setString(3, p.getCodigo().trim());
            ps.setInt(4, p.getId());
            if (ps.executeUpdate() == 0) throw new RuntimeException("Proveedor no existe.");
        } catch (SQLException e) {
            if ("23505".equals(e.getSQLState()))
                throw new RuntimeException("Código ya existe.");
            throw new RuntimeException("Error al actualizar proveedor: " + e.getMessage(), e);
        }
    }

    public void eliminar(int id) {
        String sql = "DELETE FROM proveedor WHERE id=?";
        try (Connection cn = ConexionBDProv.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) throw new RuntimeException("Proveedor no existe.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar proveedor: " + e.getMessage(), e);
        }
    }

    public Proveedor buscarPorId(int id) {
        String sql = "SELECT id, nombre, telefono, codigo FROM proveedor WHERE id=?";
        try (Connection cn = ConexionBDProv.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Proveedor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("codigo"));
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar proveedor: " + e.getMessage(), e);
        }
    }

    private void validar(Proveedor p) {
        if (p == null) throw new IllegalArgumentException("Proveedor requerido.");
        if (p.getNombre() == null || p.getNombre().trim().isEmpty())
            throw new IllegalArgumentException("El nombre es obligatorio.");
        if (p.getNombre().length() > 120)
            throw new IllegalArgumentException("El nombre no puede exceder 120 caracteres.");
        if (p.getCodigo() == null || p.getCodigo().trim().isEmpty())
            throw new IllegalArgumentException("El código es obligatorio.");
        if (p.getCodigo().length() > 30)
            throw new IllegalArgumentException("El código no puede exceder 30 caracteres.");
        if (p.getTelefono() != null && p.getTelefono().length() > 40)
            throw new IllegalArgumentException("El teléfono no puede exceder 40 caracteres.");
    }

    private String trimOrNull(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
    
}
