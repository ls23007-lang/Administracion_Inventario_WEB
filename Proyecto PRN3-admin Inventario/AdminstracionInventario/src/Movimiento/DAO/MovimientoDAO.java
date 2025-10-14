/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Movimiento.DAO;

import Movimiento.modelo.Movimiento;
//import modelo.Conexion;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

/**
 *
 * @author Usuario
 */
public class MovimientoDAO {
    
    public boolean registrarMovimiento(Movimiento m) {
        String sql = "INSERT INTO movimiento (tipo, id_producto, marca, modelo, cantidad, costo_unitario, fecha, id_proveedor) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getTipo());
            ps.setInt(2, m.getId_producto());
            ps.setString(3, m.getMarca());
            ps.setString(4, m.getModelo());
            ps.setInt(5, m.getCantidad());
            ps.setDouble(6, m.getCosto_unitario());
            ps.setDate(7, Date.valueOf(m.getFecha()));
            ps.setInt(8, m.getId_proveedor());

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("Error al registrar movimiento: " + e.getMessage());
            return false;
        }
    }
    
    public boolean actualizarMovimiento(Movimiento m) {
        String sql = "UPDATE movimiento SET tipo=?, id_producto=?, marca=?, modelo=?, cantidad=?, costo_unitario=?, fecha=?, id_proveedor=? WHERE id=?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, m.getTipo());
            ps.setInt(2, m.getId_producto());
            ps.setString(3, m.getMarca());
            ps.setString(4, m.getModelo());
            ps.setInt(5, m.getCantidad());
            ps.setDouble(6, m.getCosto_unitario());
            ps.setDate(7, Date.valueOf(m.getFecha()));
            ps.setInt(8, m.getId_proveedor());
            ps.setInt(9, m.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("Error al actualizar movimiento: " + e.getMessage());
            return false;
        }
    }

    public List<Movimiento> listarMovimientos() {
        List<Movimiento> lista = new ArrayList<>();
        String sql = "SELECT * FROM movimiento ORDER BY id DESC";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Movimiento m = new Movimiento();
                m.setId(rs.getInt("id"));
                m.setTipo(rs.getString("tipo"));
                m.setId_producto(rs.getInt("id_producto"));
                m.setMarca(rs.getString("marca"));
                m.setModelo(rs.getString("modelo"));
                m.setCantidad(rs.getInt("cantidad"));
                m.setCosto_unitario(rs.getDouble("costo_unitario"));
                m.setFecha(rs.getDate("fecha").toString());
                m.setId_proveedor(rs.getInt("id_proveedor"));
                lista.add(m);
            }

        } catch (Exception e) {
            System.err.println("Error al listar movimientos: " + e.getMessage());
        }
        return lista;
    }
}
