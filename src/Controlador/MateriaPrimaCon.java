
package Controlador;

import Controlador.Conexion;
import Modelo.Config;
import Modelo.MateriaPrima;
import Modelo.Proveedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MateriaPrimaCon {
    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    
    public boolean RegistrarMateriaPrima(MateriaPrima mat){
        String sql = "INSERT INTO materia_prima(codigo, nombre, proveedor, cantidad, precio) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, mat.getCodigo());
            ps.setString(2, mat.getNombre());
            ps.setInt(3, mat.getProveedor());
            ps.setInt(4, mat.getCantidad());
            ps.setDouble(5, mat.getPrecio());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    public List ListarMateria(){
       List<MateriaPrima> ListaMat = new ArrayList();
       String sql = "SELECT pr.id AS id_proveedor, pr.nombre AS nombre_proveedor, p.* FROM proveedor pr INNER JOIN materia_prima p ON pr.id = p.proveedor ORDER BY p.id DESC";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               MateriaPrima mat = new MateriaPrima();
               mat.setId(rs.getInt("id"));
               mat.setCodigo(rs.getString("codigo"));
               mat.setNombre(rs.getString("nombre"));
               mat.setProveedor(rs.getInt("id_proveedor"));
               mat.setProveedorPro(rs.getString("nombre_proveedor"));
               mat.setCantidad(rs.getInt("cantidad"));
               mat.setPrecio(rs.getDouble("precio"));
               ListaMat.add(mat);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ListaMat;
   }
    
    public boolean EliminarMateriaPrima(int id){
       String sql = "DELETE FROM materia_prima WHERE id = ?";
       try {
           ps = con.prepareStatement(sql);
           ps.setInt(1, id);
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               con.close();
           } catch (SQLException ex) {
               System.out.println(ex.toString());
           }
       }
   }
    
    public boolean ModificarMateriaPrima(MateriaPrima mat){
       String sql = "UPDATE materia_prima  SET codigo=?, nombre=?, proveedor=?, cantidad=?, precio=? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);
           ps.setString(1, mat.getCodigo());
           ps.setString(2, mat.getNombre());
           ps.setInt(3, mat.getProveedor());
           ps.setInt(4, mat.getCantidad());
           ps.setDouble(5, mat.getPrecio());
           ps.setInt(6, mat.getId());
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
    
    public MateriaPrima BuscarMateriaPrima(String cod){
        MateriaPrima materia = new MateriaPrima();
        String sql = "SELECT * FROM materia_prima  WHERE codigo = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, cod);
            rs = ps.executeQuery();
            if (rs.next()) {
                materia.setId(rs.getInt("id"));
                materia.setNombre(rs.getString("nombre"));
                materia.setPrecio(rs.getDouble("precio"));
                materia.setCantidad(rs.getInt("cantidad"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return materia;
    }
    public MateriaPrima BuscarId(int id){
        MateriaPrima mat = new MateriaPrima();
        String sql = "SELECT pr.id AS id_proveedor, pr.nombre AS nombre_proveedor, p.* FROM proveedor pr INNER JOIN materia_prima  p ON p.proveedor = pr.id WHERE p.id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                mat.setId(rs.getInt("id"));
                mat.setCodigo(rs.getString("codigo"));
                mat.setNombre(rs.getString("nombre"));
                mat.setProveedor(rs.getInt("proveedor"));
                mat.setProveedorPro(rs.getString("nombre_proveedor"));
                mat.setCantidad(rs.getInt("cantidad"));
                mat.setPrecio(rs.getDouble("precio"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return mat;
    }
    public Proveedor BuscarProveedor(String nombre){
        Proveedor pr = new Proveedor();
        String sql = "SELECT * FROM proveedor WHERE nombre = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            if (rs.next()) {
                pr.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return pr;
    }
    public Config BuscarDatos(){
        Config conf = new Config();
        String sql = "SELECT * FROM config";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                conf.setId(rs.getInt("id"));
                conf.setRuc(rs.getString("ruc"));
                conf.setNombre(rs.getString("nombre"));
                conf.setTelefono(rs.getString("telefono"));
                conf.setDireccion(rs.getString("direccion"));
                conf.setMensaje(rs.getString("mensaje"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return conf;
    }
    
    public boolean ModificarDatos(Config conf){
       String sql = "UPDATE config SET ruc=?, nombre=?, telefono=?, direccion=?, mensaje=? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);
           ps.setString(1, conf.getRuc());
           ps.setString(2, conf.getNombre());
           ps.setString(3, conf.getTelefono());
           ps.setString(4, conf.getDireccion());
           ps.setString(5, conf.getMensaje());
           ps.setInt(6, conf.getId());
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
}
