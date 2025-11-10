package Categoria.app.dao;

import java.util.List;
import Categoria.app.domain.Categoria;

public interface CategoriaDao {
    List<Categoria> listar();
    Categoria buscarPorId(int id);
    void insertar(Categoria c);
    void actualizar(Categoria c);
    void eliminar(int id);
}
