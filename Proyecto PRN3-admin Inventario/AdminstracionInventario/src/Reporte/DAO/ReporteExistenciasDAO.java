/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reporte.DAO;

import Conexiondb.Conexion;
import Producto.modelo.Producto;
import Reporte.tmodel.ExistenciaTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Dell
 */
public class ReporteExistenciasDAO {
    private ExistenciaTableModel existenciaTModel = new ExistenciaTableModel();//Instancia 

//Mètodo para obtener los datos
    public void getProductos() {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT id_producto, nombre, marca, modelo, id_categoria, id_proveedor, costoUnitario, cantidad FROM productos";

        try {
            con = Conexion.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                /*rs.getInt("idProducto"),
                    rs.getString("nombre"),
                    rs.getString("marca"),
                    rs.getString("modelo"),
                    rs.getInt("cantidad"),
                    rs.getDouble("costoUnitario"),
                    rs.getInt("idCategoria"),
                    rs.getInt("idProveedor")
                );*/  //Reestructuraciòn de codigo
                p.setId(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));  
                p.setCategoriaId(rs.getInt("id_categoria"));
                p.setProveedorId(rs.getInt("id_proveedor"));
                p.setCostoUnitario(rs.getDouble("costounitario"));
                p.setCantidad(rs.getInt("cantidad"));
                this.existenciaTModel.addProducto(p);        

            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
    }
}
