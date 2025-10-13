/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reporte.tmodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import Producto.modelo.Producto;

/**
 *
 * @author Dell
 */
public class ExistenciaTableModel extends AbstractTableModel {

    List<Producto> productos = new ArrayList<Producto>();

    
    @Override
    public int getRowCount() {
        return productos.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Producto producto = productos.get(rowIndex);
        Object valor = null;
        switch (columnIndex) {
            case 0:
                valor = producto.getId();
                break;
            case 1:
                valor = producto.getNombre();
                break;
            case 2:
                valor = producto.getMarca();
                break;
            case 3:
                valor = producto.getModelo();
                break;
            case 4:
                valor = producto.getCantidad();
                break;
            case 5:
                valor = producto.getCostoUnitario();
                break;
            case 6:
                valor = producto.getCategoriaId();
                break;
            case 7:
                valor = producto.getProveedorId();
        }
        return valor;
    }
    public void addProducto(Producto p){
    productos.add(p);
    
}

}
