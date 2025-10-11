/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Movimiento.modelo;

/**
 *
 * @author Usuario
 */
public class Producto {
    private int id_producto;
    private String nombre;
    private String marca;
    private String modelo;

    public Producto() {
    }

    public Producto(int id_producto, String nombre, String marca, String modelo) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return id_producto + " - " + nombre;
    }
}
