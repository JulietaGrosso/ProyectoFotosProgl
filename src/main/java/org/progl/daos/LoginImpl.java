package org.progl.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import org.progl.entities.Cuenta;
import org.progl.entities.Imagen;
import org.progl.interfaces.AdmConexiones;
import org.progl.interfaces.Dao;


public class LoginImpl implements Dao<Cuenta,String>{


  private  static final String  SQL_GETBYEMAIL= "SELECT * FROM cuenta WHERE correo = ? " ;

   

    public Cuenta getByEmail(String correo) throws SQLException {
        Cuenta cuenta = null;

        try (Connection conn = AdmConexiones.INSTANCE.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(SQL_GETBYEMAIL)) {

            pst.setString(1, correo);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    cuenta = new Cuenta();
                    cuenta.setId(rs.getInt("id"));
                    cuenta.setNombre(rs.getString("nombre"));
                    cuenta.setCorreo(rs.getString("correo"));
                    cuenta.setContrasena(rs.getString("contrasena"));
                    cuenta.setTipo(rs.getString("tipo"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cuenta;
  }



    @Override
    public List<Cuenta> getAll() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }



    @Override
    public void insert(Cuenta objeto) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }



    @Override
    public void update(Cuenta objeto) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'update'");
    }



    @Override
    public void delete(String id) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }



    @Override
    public Cuenta getById(String id) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }



    @Override
    public boolean existsById(String id) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'existsById'");
    }


 }

          


  

  
          
        

      

      
