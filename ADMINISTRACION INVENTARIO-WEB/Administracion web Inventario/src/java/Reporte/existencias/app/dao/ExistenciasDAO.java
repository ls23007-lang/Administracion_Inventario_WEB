/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reporte.existencias.app.dao;

import Conexion.DB;
import Producto.app.modelo.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dell
 */
public class ExistenciasDAO {
    public List<Producto> getExistencias() {
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
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));
                p.setCategoria(rs.getString("categoria"));
                p.setProveedor(rs.getString("proveedor"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setCostoUnitario(rs.getDouble("costounitario")); 
                lista.add(p);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error listando productos", e);
        }

        return lista;
    }
    
}
