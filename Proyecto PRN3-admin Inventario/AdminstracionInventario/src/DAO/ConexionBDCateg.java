
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ConexionBDCateg {
    
     private static final String URL  = "jdbc:postgresql://localhost:5432/practica2";
    private static final String USER = "practica2";
    private static final String PASS = "practica2";

    static { // mensaje claro si falta el driver
        try { Class.forName("org.postgresql.Driver"); }
        catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontró el driver de PostgreSQL (org.postgresql.Driver). Agrega el JAR.", e);
        }
    }

    /** Devuelve una conexión nueva. Cierra con try-with-resources. */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
    
   }