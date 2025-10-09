
package modelo;



public class Categoria {
    public Integer id;          
    public String  nombre;      
    public String  codigo;    
    public String descripcion;

    public Categoria(Integer id, String nombre, String codigo, String descripcion) {
        this.id = id; 
        this.nombre = nombre; 
        this.codigo = codigo; 
        this.descripcion = descripcion;
        
    }
    
    public Categoria(String codigo, String nombre, String descripcion) {
        this(null, codigo, nombre, descripcion);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override public String toString() { return codigo + " - " + nombre; }
    
}
