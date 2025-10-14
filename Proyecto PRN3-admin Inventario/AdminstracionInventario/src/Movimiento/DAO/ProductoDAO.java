/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Movimiento.DAO;

import java.sql.*;
import java.util.*;
import Movimiento.modelo.Producto;
//import modelo.Conexion;

/**
 *
 * @author Usuario
 */
public class ProductoDAO {
    
    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, marca, modelo FROM productos ORDER BY id_producto";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId_producto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));
                lista.add(p);
            }
        } catch (Exception e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    public Producto obtenerProductoPorId(int id) {
        Producto p = null;
        String sql = "SELECT id_producto, nombre, marca, modelo FROM productos WHERE id_producto = ?";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Producto();
                p.setId_producto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));
            }

        } catch (Exception e) {
            System.err.println("Error al obtener producto: " + e.getMessage());
        }
        return p;
    }
}
