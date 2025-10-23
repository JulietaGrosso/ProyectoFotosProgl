package org.progl.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import org.progl.entities.Cuenta;
import org.progl.entities.Imagen;
import org.progl.interfaces.AdminConexiones;
import org.progl.interfaces.Dao;


public class LoginImpl implements Dao<Cuenta,String>, AdminConexiones{
  private Connection conn= null;


  private  static final String  SQL_GETBYEMAIL= "SELECT * FROM cuenta WHERE correo = ? " ;

   

    public Cuenta getByEmail(String correo) {
          conn = AdminConexiones.obtenerConexion();
        // Se crea un statement
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean existe = false;
        Cuenta cuenta = null;

         try {
          pst = conn.prepareStatement(SQL_GETBYEMAIL); // CREO STATEMENT
          pst.setString(1,correo);
          rs = pst.executeQuery(); //EJECUTO CONSULTA
          // SI LA CONSULTA DEVUELVE AL MENOS UN REGISTRO, EXISTE
          if (rs.next()) {
             cuenta = new Cuenta();
              // asigno los datos a auto
              cuenta.setId(rs.getInt("id"));
              cuenta.setNombre(rs.getString("nombre"));
              cuenta.setCorreo(rs.getString("correo"));
              cuenta.setContrasena(rs.getString("contrasena"));
           
             
            }

            // Cierro Resultset y Statement
            rs.close();
            pst.close();
            conn.close();
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

          


  

  
          
        

      

      
