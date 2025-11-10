package Proveedor.app.domain;

public class Proveedor {
    private Integer id;       // mapeado desde id_proveedor
    private String nombre;
    private String telefono;
    private String codigo;

    public Proveedor() {}

    public Proveedor(Integer id, String nombre, String telefono, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.codigo = codigo;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
}
