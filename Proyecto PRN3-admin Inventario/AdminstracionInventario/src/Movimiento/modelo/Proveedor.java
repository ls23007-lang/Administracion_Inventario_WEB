/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Movimiento.modelo;

/**
 *
 * @author Usuario
 */
public class Proveedor {
    
    private int id_proveedor;
    private String nombre;

    public Proveedor() {
    }

    public Proveedor(int id_proveedor, String nombre) {
        this.id_proveedor = id_proveedor;
        this.nombre = nombre;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    
}
