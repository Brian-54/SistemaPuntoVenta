
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private String myBD="jdbc:mysql://localhost:3307/sistemaventa?serverTimezone=UTC";
    private String user="root";
    private String pass="";
    Connection con;
    
    public Connection getConnection(){
        try {
            con = DriverManager.getConnection(myBD,user,pass);
            return con;
        } catch (SQLException e) {
            System.out.println(e.toString());           
        }
    return null;
    }
}
