
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ClienteDao {
    //Intanciacion de objeto
    Conexion cn=new Conexion();
    //Declaramos la varible con de tipo Connection
    Connection con;
    //Declaramos la varible ps de tipo PreparedStatement 
    PreparedStatement ps;
    //Declaramos la varible rs de tipo ResultSet
    ResultSet rs;
    
    //Metodo booleano que recibe un objeto cliente
    public boolean RegistrarCliente(Cliente cl){
        String sql="INSERT INTO clientes(dni,nombre,telefono,direccion,razon) VALUES (?,?,?,?,?)";
        try {
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            //Enviamos los parametros y ejecutamos consulta
            ps.setInt(1, cl.getDni());
            ps.setString(2, cl.getNombre());
            ps.setInt(3, cl.getTelefono());
            ps.setString(4, cl.getDireccion());
            ps.setString(5, cl.getRazon());
            ps.execute();
            //Si se ejecuto correctamente retornamos un true
            return true;
        } catch (SQLException e) {
            //Si hay error lo mostramos y retornamos false
            JOptionPane.showMessageDialog(null,e.toString());
            return false;
            //Cerramos la conexion
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    //Metodo Lista cliente
    public List ListarCliente(){
        //Intanciamos objeto ListaCl
        List<Cliente> ListaCl= new ArrayList();
        String sql="SELECT * FROM clientes";
        try {
            con=cn.getConnection();
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            //el rs se coloca antes de la primera fila y empieza a recorrer todas las filas de la tabla 
            while(rs.next()){
                //Intanciamos el objeto cl para almacenar los datos obtenidos en la consulta
                Cliente cl=new Cliente();
                cl.setInt(rs.getInt("id"));
                cl.setDni(rs.getInt("dni"));
                cl.setNombre(rs.getString("nombre"));
                cl.setTelefono(rs.getInt("telefono"));
                cl.setDireccion(rs.getString("direccion"));
                cl.setRazon(rs.getString("razon"));
                //Iremos agregando al arraylist el objeto
                ListaCl.add(cl);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        //Retornamos el arraylist del cliente
        return ListaCl;
    }
    
    //Metodo booleano que recibe un int
    public boolean EliminarCliente(int id){
        String sql="DELETE FROM clientes WHERE id=?";
        try {
            ps=con.prepareStatement(sql);
            ps.setInt(1,id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public boolean ModificarCliente(Cliente cl){
        String sql="UPDATE clientes SET dni=?,nombre=?,telefono=?,direccion=?,razon=? WHERE id=?";
        try {
            ps=con.prepareStatement(sql);
            ps.setInt(1,cl.getDni());
            ps.setString(2, cl.getNombre());
            ps.setInt(3, cl.getTelefono());
            ps.setString(4, cl.getDireccion());
            ps.setString(5, cl.getRazon());
            ps.setInt(6, cl.getId());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        } finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
    
    public Cliente BuscarCliente(int dni){
        Cliente cl=new Cliente();
        String slq="SELECT * FROM clientes WHERE dni=?";
        try {
            con=cn.getConnection();
            ps=con.prepareStatement(slq);
            ps.setInt(1, dni);
            rs = ps.executeQuery();
            if(rs.next()){
               cl.setNombre(rs.getString("nombre"));
               cl.setTelefono(rs.getInt("telefono"));
               cl.setDireccion(rs.getString("direccion"));
               cl.setRazon(rs.getString("razon"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cl;
    }
}
