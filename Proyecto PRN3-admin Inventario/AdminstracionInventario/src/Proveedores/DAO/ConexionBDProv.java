
package Proveedores.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBDProv {
    private static final String URL  = "jdbc:postgresql://localhost:5432/inventario_usuario";
    private static final String USER = "inventario_usuario";
    private static final String PASSWORD = "inventario";

     public static Connection getConnection() {
        Connection con = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a PostgreSQL");
        } catch (ClassNotFoundException e) {
            System.err.println("No se encontró el Driver de PostgreSQL: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
        return con;
    }
}