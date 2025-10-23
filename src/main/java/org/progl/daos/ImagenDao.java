package org.progl.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.progl.entities.Imagen;
import org.progl.interfaces.AdminConexiones;

public class ImagenDao {

      private static Connection conn;
    
      public void update(Imagen imagen) {
    // establecer conexion

    // solo si la imágen existe se modifica
    if (this.existsById(imagen.getId())) {
      //
      String sql = "UPDATE imagen SET " +
          "foto = '" + imagen.getFoto() + "', " +
          "nombre = '" + imagen.getNombre()  + "', " +
          "alt = '" + imagen.getAlt();

      conn = AdminConexiones.obtenerConexion();
      // Se crea un statement
      Statement st = null;
         try {
        // ejecuto
        st = conn.createStatement(); // CREO STATEMENT
        st.execute(sql);
        //cierro
        st.close();
        conn.close();

      } catch (SQLException e) {
        System.out.println("Error al crear el statement");
      }
    }

  }

  public boolean existsById(int id) {
    // establecer conexion
    conn = AdminConexiones.obtenerConexion();
    String sql = "SELECT * FROM imagen WHERE id = " + id;
    // Se crea un statement
    Statement st = null;
    ResultSet rs = null;
    boolean existe = false;
    try {
      st = conn.createStatement(); // CREO STATEMENT
      rs = st.executeQuery(sql); //EJECUTO CONSULTA
      // SI LA CONSULTA DEVUELVE AL MENOS UN REGISTRO, EXISTE
      if (rs.next()) {
        existe = true;
      }
      // CIERRO RESULTSET Y STATEMENT
      rs.close();
      st.close();
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return existe;
  }

  public void insertarImagen(Imagen imagen) {

    // 1 establecer conexion
    conn = AdminConexiones.obtenerConexion();
    // establecer conexion a la base de datos

    // paso 2 crear string consulta SQL
    String sql =
        "INSERT INTO imagen (id, foto, nombre, alt) " +
            "VALUES (null," +
            "'" + imagen.getFoto() + "'," +
            "'" + imagen.getNombre() + "'," +
            "'" + imagen.getAlt() + "')";

    // paso 3 crear instruccion
    Statement st = null;
    try {
      st = conn.createStatement();

      // paso 4 ejecutar instruccion
      st.execute(sql);

      // paso 5 cerrar conexion
      st.close();
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }


  }

  public List<Imagen> findAll() {

    //1 conectar
    conn = AdminConexiones.obtenerConexion();

    //2  crear consulta SQL
    String sql = "SELECT * FROM imagen order by id";

    // 3 crear  statement y resulset
    Statement st = null;
    ResultSet rs = null;

    List<Imagen> listaImagenes = new java.util.ArrayList<>();

    try {
      // paso 3 crear instruccion
      st = conn.createStatement();
      // paso 4 ejecutar consulta y guarda el resultado en resultset
      rs = st.executeQuery(sql);

      // paso 5 recorrer el resultset y guardar las imágenes en la lista
      while (rs.next()) {
        Imagen imagen = new Imagen();
        imagen.setFoto(rs.getString("foto"));
        imagen.setNombre(rs.getString("nombre"));
        imagen.setAlt(rs.getString("alt"));
       
       
       listaImagenes.add(imagen);
      }

      // paso 6 cerrar el resultset y statement
      rs.close();
      st.close();
      conn.close();


    } catch (SQLException e) {
      System.out.println("Error al crear el statement");
      throw new RuntimeException(e);
    }


    return listaImagenes;
  }

  public void delete(int id){

    conn= AdminConexiones.obtenerConexion();
    String sql = "DELETE FROM imagen WHERE id = " + id;
    Statement st=null;
    try {
      st=conn.createStatement(); //creo statement
      st.execute(sql); //ejecuto la consulta
      st.close(); //cierro statement
      conn.close();// cierro conexion

    } catch (SQLException e) {
      System.out.println("Error al crear el statement");
    }
  }

public Imagen getById(int id) {
    // establecer conexion
    conn = AdminConexiones.obtenerConexion();
    String sql = "SELECT * FROM imagen WHERE id = " + id;
    // Se crea un statement
    Statement st = null;
    ResultSet rs = null;
    Imagen imagen = new Imagen();
    try {
      st = conn.createStatement(); // CREO STATEMENT
      rs = st.executeQuery(sql); //EJECUTO CONSULTA
      // SI LA CONSULTA DEVUELVE AL MENOS UN REGISTRO, EXISTE
      if (rs.next()) {
            // asigno los datos a la imágen
            imagen.setId(rs.getInt(id));
            imagen.setFoto(rs.getString("foto"));
            imagen.setNombre(rs.getString("nombre"));
            imagen.setAlt(rs.getString("alt"));
            
      }
      // CIERRO RESULTSET Y STATEMENT
      rs.close();
      st.close();
      conn.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return imagen;
  }


  }


