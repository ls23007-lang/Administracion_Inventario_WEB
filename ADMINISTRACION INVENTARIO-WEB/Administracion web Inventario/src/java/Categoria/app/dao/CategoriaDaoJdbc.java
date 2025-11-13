package Categoria.app.dao;

import Conexion.DB;
import Categoria.app.dao.CategoriaDao;
import Categoria.app.domain.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDaoJdbc implements CategoriaDao {

    public CategoriaDaoJdbc() { }

    @Override
    public List<Categoria> listar() {
  

       
         String sql = "SELECT id_categoria AS id, nombre, descripcion FROM public.categorias ORDER BY id_categoria";

        List<Categoria> out = new ArrayList<>();
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Categoria(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion")));
            }
        } catch (Exception e) {
            // Deja el mensaje claro y conserva la causa real en el log del servidor
            throw new RuntimeException("Error al listar categorías", e);
        }
        return out;
    }

    @Override
    public Categoria buscarPorId(int id) {
        
         String sql = "SELECT id_categoria AS id, nombre, descripcion FROM public.categorias WHERE id_categoria = ?";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Categoria(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar categoría", e);
        }
        return null;
    }

    @Override
    public void insertar(Categoria c) {
        // PostgreSQL: devuelve el id insertado
       
         String sql = "INSERT INTO public.categorias (nombre, descripcion) VALUES (?, ?) RETURNING id_categoria AS id";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) c.setId(rs.getInt("id"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al insertar categoría", e);
        }
    }

    @Override
    public void actualizar(Categoria c) {
        
        String sql = "UPDATE public.categorias SET nombre = ?, descripcion = ? WHERE id_categoria = ?";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, c.getNombre());
            ps.setString(2, c.getDescripcion());
            ps.setInt(3, c.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar categoría", e);
        }
    }

    @Override
    public void eliminar(int id) {
       
        String sql = "DELETE FROM public.categorias WHERE id_categoria = ?";

        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar categoría", e);
        }
    }
}
