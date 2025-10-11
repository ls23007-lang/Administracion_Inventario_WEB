
package Categorias;



public class Categoria {
    public Integer id;          
    public String  nombre;      
    public String descripcion;

    public Categoria(Integer id, String nombre, String descripcion) {
        this.id = id; 
        this.nombre = nombre; 
        this.descripcion = descripcion;
        
    }
    
    public Categoria( String nombre, String descripcion) {
        this(null, nombre, descripcion);
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

   

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    @Override public String toString() { return   " - " + nombre; }
    
}
