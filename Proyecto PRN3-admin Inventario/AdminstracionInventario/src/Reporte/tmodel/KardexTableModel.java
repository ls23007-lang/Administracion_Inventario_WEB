/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reporte.tmodel;

import Movimiento.modelo.Movimiento;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dell
 */
public class KardexTableModel extends AbstractTableModel {

    List<Movimiento> movimientos = new ArrayList<Movimiento>();

    @Override
    public int getRowCount() {
        return movimientos.size();
    }

    @Override
    public int getColumnCount() {
        return 9;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Movimiento movimiento = movimientos.get(rowIndex);
        Object valor = null;
        switch (columnIndex) {
            case 0:
                valor = movimiento.getId();
                break;
            case 1:
                valor = movimiento.getTipo();
                break;
            case 2:
                valor = movimiento.getId_producto();
                break;
            case 3:
                valor = movimiento.getMarca();
                break;
            case 4:
                valor = movimiento.getModelo();
                break;
            case 5:
                valor = movimiento.getCantidad();
                break;
            case 6:
                valor = movimiento.getCosto_unitario();
                break;
            case 7:
                valor = movimiento.getFecha();
                break;
            case 8:
                valor = movimiento.getId_proveedor();
                break;
        }
        return valor;
    }

    public void addMovimiento(Movimiento m) {
        movimientos.add(m);

    }
}
