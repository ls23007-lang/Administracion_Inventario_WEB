/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Producto.modelo;

/**
 *
 * @author ander
 */
public class Producto {
    private int id;
    private String nombre;
    private String marca;
    private String modelo;
    private int cantidad;
    private double costoUnitario;
    
    // Claves Foráneas: Usamos int para guardar solo el ID de la relación
    private int categoriaId; 
    private int proveedorId; 

    // Constructor 1: Para CREAR/REGISTRAR un nuevo producto (sin ID, ya que la BD lo genera)
    public Producto(String nombre, String marca, String modelo, int cantidad, double costoUnitario, int categoriaId, int proveedorId) {
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.categoriaId = categoriaId;
        this.proveedorId = proveedorId;
    }

    // Constructor 2: Para OBTENER/CARGAR un producto existente de la BD (incluye ID)
    public Producto(int id, String nombre, String marca, String modelo, int cantidad, double costoUnitario, int categoriaId, int proveedorId) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.modelo = modelo;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.categoriaId = categoriaId;
        this.proveedorId = proveedorId;
    }
    
    // --- Getters y Setters ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(double costoUnitario) { this.costoUnitario = costoUnitario; }

    public int getCategoriaId() { return categoriaId; }
    public void setCategoriaId(int categoriaId) { this.categoriaId = categoriaId; }

    public int getProveedorId() { return proveedorId; }
    public void setProveedorId(int proveedorId) { this.proveedorId = proveedorId; }
    
}
