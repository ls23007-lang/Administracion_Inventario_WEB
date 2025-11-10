package Proveedor.app.dao;

import Proveedor.app.domain.Proveedor;
import java.util.List;

public interface ProveedorDao {
    List<Proveedor> listar();
    Proveedor buscarPorId(int id);
    void insertar(Proveedor p);
    void actualizar(Proveedor p);
    void eliminar(int id);
}
