
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAO {
    //Declaramos la varible con de tipo Connection
    Connection con;
    //Declaramos la varible ps de tipo PreparedStatement
    PreparedStatement ps;
    //Declaramos la varible rs de tipo ResultSet
    ResultSet rs;
    //Instanciación de objetos
    Conexion cn = new Conexion();;
    login l = new login();
    
    public login log(String correo,String pass){ 
        String sql = "SELECT * FROM usuarios WHERE correo= ? AND pass = ?";  
        try {    
            //la variable con recibe la conexion
            con=cn.getConnection();
            //la variable ps recibe la consulta
            ps = con.prepareStatement(sql);    
            //Los campos a buscar
            ps.setString(1,correo);
            ps.setString(2,pass);
            //Ejecuccion de query
            rs=ps.executeQuery();
            //Si la siguiente fila no es null significa que existe,ya que el rs se coloca antes de la primera fila
            if(rs.next()){
            //Los datos que se obtengan se guardaran en el objeto l
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                l.setCorreo(rs.getString("correo"));
                l.setPass(rs.getString("pass"));
                l.setRol(rs.getString("rol"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        //Retornamos objeto
        return l;
    }
    
    public boolean Registrar(login reg){
        String sql="INSERT INTO usuarios(nombre,correo,pass,rol)VALUES(?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, reg.getNombre());
            ps.setString(2, reg.getCorreo());
            ps.setString(3, reg.getPass());
            ps.setString(4, reg.getRol());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
