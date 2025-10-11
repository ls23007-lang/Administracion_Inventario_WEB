
package Categorias.DAO;

import Categorias.modelo.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<Categoria> listar() {
        String sql = "SELECT id, nombre, descripcion FROM categoria ORDER BY id";
        List<Categoria> data = new ArrayList<>();
        try (Connection cn = ConexionBDCateg.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                data.add(new Categoria(
                        rs.getInt("id"),
                        
                        rs.getString("nombre"),
                        rs.getString("descripcion")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al listar categorías: " + e.getMessage(), e);
        }
        return data;
    }

    public Categoria insertar(Categoria c) {
        validar(c, false);
        String sql = "INSERT INTO categoria(nombre, descripcion) VALUES(?, ?) RETURNING id";
        try (Connection cn = ConexionBDCateg.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

           
            ps.setString(1, c.getNombre().trim());
            
            ps.setString(2, c.getDescripcion() == null ? null : c.getDescripcion().trim());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
            return c;
        } catch (SQLException e) {
        if (esDuplicado(e)) throw new RuntimeException(" nombre ya existe.");
        throw new RuntimeException("Error al insertar categoría: " + e.getMessage(), e);
    }
    }

  public void actualizar(Categoria c) {
    if (c.getId() == null) throw new IllegalArgumentException("ID requerido para actualizar.");
    validar(c, true);
    String sql = "UPDATE categoria SET  nombre=?, descripcion=? WHERE id=?";
    try (Connection cn = ConexionBDCateg.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        ps.setString(1, c.getNombre().trim());
        ps.setString(2, c.getDescripcion() == null ? null : c.getDescripcion().trim());
        ps.setInt(3, c.getId());

        if (ps.executeUpdate() == 0) throw new RuntimeException("Categoría no existe.");
    } catch (SQLException e) {
        if (esDuplicado(e)) throw new RuntimeException("nombre ya existe.");
        throw new RuntimeException("Error al actualizar: " + e.getMessage(), e);
    }
}

    public void eliminar(int id) {
        String sql = "DELETE FROM categoria WHERE id=?";
        try (Connection cn = ConexionBDCateg.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) throw new RuntimeException("Categoría no existe.");
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar: " + e.getMessage(), e);
        }
    }

  public Categoria buscarPorNombre(String nombre) {
    String sql = "SELECT id,  descripcion FROM categoria WHERE nombre=?";
    try (Connection cn = ConexionBDCateg.getConnection();
         PreparedStatement ps = cn.prepareStatement(sql)) {
        ps.setString(1, nombre);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return new Categoria(
                    rs.getInt("id"),
                    
                    rs.getString("nombre"),
                    rs.getString("descripcion"));
            }
            return null;
        }
    } catch (SQLException e) {
        throw new RuntimeException("Error al buscar por nombre: " + e.getMessage(), e);
    }
}

    private void validar(Categoria c, boolean esUpdate) {
    if (c == null) throw new IllegalArgumentException("Categoría requerida.");
   
    if (c.getNombre() == null || c.getNombre().trim().isEmpty())
        throw new IllegalArgumentException("El nombre es obligatorio.");
    if (c.getNombre().length() > 80)
        throw new IllegalArgumentException("El nombre no puede exceder 80 caracteres.");
    if (c.getDescripcion() != null && c.getDescripcion().length() > 250)
        throw new IllegalArgumentException("La descripción no puede exceder 250 caracteres.");
}

    private boolean esDuplicado(SQLException e) {
        // Para Postgres: unique_violation = 23505
        return "23505".equals(e.getSQLState());
    }
}
