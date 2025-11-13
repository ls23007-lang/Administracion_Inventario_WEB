package Proveedor.app.dao;

import Conexion.DB;          // usamos tu misma clase DB
import Proveedor.app.domain.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDaoJdbc implements ProveedorDao {

    private static final String TABLE = "public.proveedores";
    private static final String COL_ID = "id_proveedor";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_TELEFONO = "telefono";
    private static final String COL_CODIGO = "codigo";

    @Override
    public List<Proveedor> listar() {
        String sql = "SELECT " + COL_ID + " AS id, " + COL_NOMBRE + ", " + COL_TELEFONO + ", " + COL_CODIGO +
                     " FROM " + TABLE + " ORDER BY " + COL_ID;
        List<Proveedor> out = new ArrayList<>();
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                out.add(new Proveedor(
                        rs.getInt("id"),
                        rs.getString(COL_NOMBRE),
                        rs.getString(COL_TELEFONO),
                        rs.getString(COL_CODIGO)
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("[DAO proveedores] Error al listar | " + e.getMessage(), e);
        }
        return out;
    }

    @Override
    public Proveedor buscarPorId(int id) {
        String sql = "SELECT " + COL_ID + " AS id, " + COL_NOMBRE + ", " + COL_TELEFONO + ", " + COL_CODIGO +
                     " FROM " + TABLE + " WHERE " + COL_ID + " = ?";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Proveedor(
                            rs.getInt("id"),
                            rs.getString(COL_NOMBRE),
                            rs.getString(COL_TELEFONO),
                            rs.getString(COL_CODIGO)
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("[DAO proveedores] Error al buscar | " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void insertar(Proveedor p) {
        // PostgreSQL: devolvemos el id insertado
        String sql = "INSERT INTO " + TABLE + " (" + COL_NOMBRE + ", " + COL_TELEFONO + ", " + COL_CODIGO + ") " +
                     "VALUES (?, ?, ?) RETURNING " + COL_ID + " AS id";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getTelefono());
            ps.setString(3, p.getCodigo());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) p.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("[DAO proveedores] Error al insertar | " + e.getMessage(), e);
        }
    }

    @Override
    public void actualizar(Proveedor p) {
        String sql = "UPDATE " + TABLE + " SET " + COL_NOMBRE + "=?, " + COL_TELEFONO + "=?, " + COL_CODIGO + "=? " +
                     "WHERE " + COL_ID + "=?";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getTelefono());
            ps.setString(3, p.getCodigo());
            ps.setInt(4, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[DAO proveedores] Error al actualizar | " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM " + TABLE + " WHERE " + COL_ID + "=?";
        try (Connection cn = DB.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("[DAO proveedores] Error al eliminar | " + e.getMessage(), e);
        }
    }
}
