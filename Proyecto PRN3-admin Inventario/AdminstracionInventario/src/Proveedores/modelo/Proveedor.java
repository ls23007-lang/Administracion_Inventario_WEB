
package Proveedores.modelo;

public class Proveedor {
   public Integer id;
   public String nombre, telefono, codigo;

    public Proveedor(Integer id, String nombre, String telefono, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.codigo = codigo;
    }
   public Proveedor(String nombre, String telefono, String codigo) {
        this(null, nombre, telefono, codigo);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    @Override public String toString() { return codigo + " - " + nombre; }
}
