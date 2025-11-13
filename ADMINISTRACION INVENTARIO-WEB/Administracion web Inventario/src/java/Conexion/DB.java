package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Conexión centralizada a PostgreSQL para la app.
 * Ajusta únicamente DB_USER si tu usuario no es "postgres".
 *
 * Requisitos:
 *  - Agregar el driver JDBC de PostgreSQL al proyecto (Ant):
 *      Project > Properties > Libraries > Compile > Add JAR/Folder...
 *      postgresql-<version>.jar
 *  - Servidor PostgreSQL accesible en localhost:5432
 *  - Base de datos: inventario_db
 *  - Usuario: postgres
 *  - Password: inventario
 */
public class DB {

    // --- CONFIG PostgreSQL ---
    private static final String DRIVER  = "org.postgresql.Driver";
    private static final String HOST    = "localhost";
    private static final int    PORT    = 5432;
    private static final String DB_NAME = "inventario_db";

    // Cambia el usuario si corresponde (por defecto en muchos entornos es "postgres")
    private static final String DB_USER = "inventario_usuario";
    private static final String DB_PASS = "inventario";

    // Construimos la URL explícitamente (útil si luego quieres parametrizar)
    private static final String DB_URL =
            "jdbc:postgresql://" + HOST + ":" + PORT + "/" + DB_NAME;

    static {
        // Carga del driver en el arranque de la clase (seguro en servidores Java EE)
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            // Si ves este error, te falta agregar el .jar del driver a las Libraries del proyecto.
            throw new RuntimeException("No se encontró el driver PostgreSQL: " + DRIVER, e);
        }
    }

    /** Obtiene una conexión nueva. Recuerda cerrar después de usar. */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (Exception e) {
            // Si falla, imprime la URL para diagnóstico (sin exponer la contraseña)
            String msg = "Error obteniendo conexión a " + DB_URL + " con usuario '" + DB_USER + "'";
            throw new RuntimeException(msg, e);
        }
    }

    /** Helpers para cerrar recursos sin ruido (opcional, por comodidad). */
    public static void closeQuietly(ResultSet rs) {
        if (rs != null) try { rs.close(); } catch (Exception ignored) {}
    }

    public static void closeQuietly(Statement st) {
        if (st != null) try { st.close(); } catch (Exception ignored) {}
    }

    public static void closeQuietly(Connection cn) {
        if (cn != null) try { cn.close(); } catch (Exception ignored) {}
    }
}
