/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reporte.DAO;

import Categorias.DAO.ConexionBDCateg;
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

        String sql = "SELECT idProducto, nombre, marca, modelo, cantidad, costoUnitario, idCategoria, idProveedor FROM producto";

        try {
            con = ConexionBDCateg.getConnection();
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
                p.setId(rs.getInt("idproducto"));
                p.setNombre(rs.getString("nombre"));
                p.setMarca(rs.getString("marca"));
                p.setModelo(rs.getString("modelo"));
                p.setCantidad(rs.getInt("cantidad"));
                p.setCostoUnitario(rs.getDouble("costounitario"));
                p.setCategoriaId(rs.getInt("idcategoria"));
                p.setProveedorId(rs.getInt("idproveedor"));
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
