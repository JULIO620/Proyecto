
package Controlador;

import Controlador.Conexion;
import Modelo.login;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginCon {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Conexion cn = new Conexion();
    
    public login log(String correo, String pass){
        login l = new login();
        String sql = "SELECT * FROM usuarios WHERE correo = ? AND pass = ? AND estado= 'activo'";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, pass);
            rs= ps.executeQuery();
            if (rs.next()) {
                l.setId(rs.getInt("id"));
                l.setNombre(rs.getString("nombre"));
                l.setCorreo(rs.getString("correo"));
                l.setPass(rs.getString("pass"));
                l.setRol(rs.getString("rol"));
                l.setEstado(rs.getString("estado"));
                
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return l;
    }
    
    public boolean Registrar(login lg){
        String sql = "INSERT INTO usuarios (nombre, correo, pass, rol, estado) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, lg.getNombre());
            ps.setString(2, lg.getCorreo());
            ps.setString(3, lg.getPass());
            ps.setString(4, lg.getRol());
             ps.setString(5, lg.getEstado());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public List ListarUsuarios(){
       List<login> Lista = new ArrayList();
       String sql = "SELECT * FROM usuarios";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               login lg = new login();
               lg.setId(rs.getInt("id"));
               lg.setNombre(rs.getString("nombre"));
               lg.setCorreo(rs.getString("correo"));
               lg.setPass(rs.getString("pass"));
               lg.setRol(rs.getString("rol"));
               lg.setEstado(rs.getString("estado"));
               Lista.add(lg);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return Lista;
   }
     public boolean ModificarUsuario(login lg){
       String sql = "UPDATE usuarios SET nombre=?, correo=?, pass=?, rol=?, estado=? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);   
           ps.setString(1, lg.getNombre());
           ps.setString(2, lg.getCorreo());
           ps.setString(3, lg.getPass());
           ps.setString(4, lg.getRol());
            ps.setString(5, lg.getEstado());
            ps.setInt(6, lg.getId());
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
   
   public login BuscarUsuario(int id){
       login l = new login();
       String sql = "SELECT * FROM usuarios WHERE id = ?";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           ps.setInt(1, id);
           rs = ps.executeQuery();
           if (rs.next()) {
               l.setId(rs.getInt("id"));
               l.setCorreo(rs.getString("correo"));
               l.setPass(rs.getString("pass"));
               l.setRol(rs.getString("rol"));
               l.setEstado(rs.getString("estado"));
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return l;
   }
}
