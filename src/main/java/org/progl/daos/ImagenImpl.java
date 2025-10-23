package org.progl.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import org.progl.entities.Imagen;
import org.progl.interfaces.AdminConexiones;
import org.progl.interfaces.Dao;


public class ImagenImpl implements Dao<Imagen,Integer>, AdminConexiones{
  private Connection conn= null;

 private static final String SQL_INSERT=
      "INSERT INTO imagen (foto, nombre, alt) " +
      "VALUES            (      ?,      ?,   ?)";


  private static  final String  SQL_UPDATE= "UPDATE imagen SET " +
      "foto = ? , nombre = ?, alt = ? " +
      "  WHERE id = ? " ;

  private static  final String  SQL_DELETE= "DELETE FROM imagen  WHERE id = ? " ;
  private static  final String  SQL_GETALL= "SELECT * FROM imagen ORDER BY id" ;
  private  static final String  SQL_GETBYID= "SELECT * FROM imagen WHERE id = ? " ;

   @Override
  public List<Imagen> getAll() {
    //1 conectar
    conn = AdminConexiones.obtenerConexion();
  
     //2  crear consulta SQL
    String sql = "SELECT * FROM imagen order by id";


    // 3 crear  statement y resulset
    PreparedStatement pst = null;
    ResultSet rs = null;

    List<Imagen> listaImagenes = new java.util.ArrayList<>();

    try {
        // paso 3 crear instruccion
        pst = conn.prepareStatement(SQL_GETALL);
        // paso 4 ejecutar consulta y guarda el resultado en resultset
        rs = pst.executeQuery();

        // paso 5 recorrer el resultset y guardar las imágenes en una lista
      while (rs.next()) {
        Imagen imagen = new Imagen();
        imagen.setId(rs.getInt("id"));
        imagen.setFoto(rs.getString("foto"));
        imagen.setNombre(rs.getString("nombre"));
        imagen.setAlt(rs.getString("alt"));
      

        listaImagenes.add(imagen);
      }

    // paso 6 cerrar el resultset y statement
        rs.close();
        pst.close();
        conn.close();

    } catch (SQLException e) {
        System.out.println("Error al crear el statement");
        throw new RuntimeException(e);
        }

     return listaImagenes;   
}


    @Override
    public void insert(Imagen imagen) {
        // Abrir conexión dentro del método
        try (Connection conn = AdminConexiones.obtenerConexion();
            PreparedStatement pst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            // Verificar si existe la imagen (abrir una nueva conexión dentro de existsById)
            if (existsById(imagen.getId())) {
                System.out.println("La imagen ya existe.");
                return;
            }

            // Asignar parámetros
            pst.setString(1, imagen.getFoto());
            pst.setString(2, imagen.getNombre());
            pst.setString(3, imagen.getAlt());

            // Ejecutar insert
            int resultado = pst.executeUpdate();

            if (resultado == 1) {
                System.out.println("Imagen insertada correctamente.");
            }

            // Obtener ID generado
            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    imagen.setId(rs.getInt(1));
                    System.out.println("ID asignado: " + imagen.getId());
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar imagen: " + e.getMessage());
        }
    }


      @Override
       public void update(Imagen objeto) {
       conn = AdminConexiones.obtenerConexion();

        Imagen imagen= objeto;
        // solo si el auto existe lo modifico
          if(this.existsById(imagen.getId())){
          conn = AdminConexiones.obtenerConexion();
              // Se crea un statement
          PreparedStatement pst = null;
          
           try {
        // ejecuto
        pst = conn.prepareStatement(SQL_UPDATE);

        pst.setString(1, imagen.getFoto());
        pst.setString(2,imagen.getNombre());
        pst.setString(3, imagen.getAlt());
       
        // paso 4 ejecutar instruccion
        // executeUpdate devuelve 1 si ejecuto correctamente 0 caso contrario
        int resultado = pst.executeUpdate();
        if (resultado == 1) {
          System.out.println("La imágen se actualizó correctamente");
        } else {
          System.out.println("No se pudo actualizar la imágen");
        }

        pst.close();
        conn.close();

      } catch (SQLException e) {
        System.out.println("Error al crear el statement");
      }
    }
    }

    @Override
      public void delete(Integer id) {
          Connection conn = AdminConexiones.obtenerConexion();

          try {
            PreparedStatement pst = conn.prepareStatement(SQL_DELETE);
            pst.setInt(1,id);
            int resultado = pst.executeUpdate();
            if (resultado == 1) {
              System.out.println("Imágen eliminada correctamente");
            } else {
              System.out.println("No se pudo eliminar la imágen");
            }
            pst.close();
            conn.close();
          } catch (SQLException e) {
            System.out.println("No se pudo eliminar la imágen. Error: " + e.getMessage());
          }

    }


    @Override
    public Imagen getById(Integer id) {
          conn = AdminConexiones.obtenerConexion();
        // Se crea un statement
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean existe = false;
        Imagen imagen = null;

         try {
          pst = conn.prepareStatement(SQL_GETBYID); // CREO STATEMENT
          pst.setInt(1,id);
          rs = pst.executeQuery(); //EJECUTO CONSULTA
          // SI LA CONSULTA DEVUELVE AL MENOS UN REGISTRO, EXISTE
          if (rs.next()) {
             imagen = new Imagen();
              // asigno los datos a auto
              imagen.setId(rs.getInt("id"));
              imagen.setFoto(rs.getString("foto"));
              imagen.setNombre(rs.getString("nombre"));
              imagen.setAlt(rs.getString("alt"));
             
            }

            // Cierro Resultset y Statement
            rs.close();
            pst.close();
            conn.close();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
          return imagen;
  }




    @Override
    public boolean existsById(Integer id) {
     conn = AdminConexiones.obtenerConexion();

     //Se crea el Statement 
        PreparedStatement pst = null;
        ResultSet rs = null;
        boolean existe = false;
     
        try {
              pst = conn.prepareStatement(SQL_GETBYID); // Creo Statement
              pst.setInt(1,id);
              rs = pst.executeQuery(); //Ejecuto la consulta
              //Si la consulta devuelve al menos un registro, existe
              if (rs.next()) {
                existe = true;
              }
              //Cierro ResultSet y Statement
              rs.close();
              pst.close();
              conn.close();
            } catch (SQLException e) {
              throw new RuntimeException(e);
            }
            return existe;
    }




 }

          


  

  
          
        

      

      
