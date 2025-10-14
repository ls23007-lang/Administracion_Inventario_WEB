/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Conexiondb;

import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        Connection cn = Conexion.getConnection();
        if (cn != null) {
            System.out.println("✅ Conexión exitosa a la base de datos.");
        } else {
            System.out.println("❌ No se pudo conectar.");
        }
    }
}
