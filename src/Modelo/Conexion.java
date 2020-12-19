
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private String myBD="jdbc:mysql://localhost:3307/sistemaventa?serverTimezone=UTC";
    private String user="root";
    private String pass="";
    //Declaramos la varible con de tipo Connection
    Connection con;
    
    public Connection getConnection(){
        try {
            // ya no es necesario en las nuevas versiones ocupar el Class.forname(driver);
            //Se crea la conexion,si hay error entramos al catch y se finaliza el proceso con null  
            con = DriverManager.getConnection(myBD,user,pass);
            return con;
        } catch (SQLException e) {
            System.out.println(e.toString());           
        }
    return null;
    }
}
