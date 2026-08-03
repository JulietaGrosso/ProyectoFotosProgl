package org.progl.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.List;

import org.progl.entities.Imagen;
import org.progl.interfaces.AdmConexiones;
import org.progl.interfaces.Dao;


public class ImagenImpl implements Dao<Imagen,Integer>{

 private static final String SQL_INSERT=
      "INSERT INTO imagen (foto, nombre, alt) " +
      "VALUES            (      ?,      ?,   ?)";


  private static  final String  SQL_UPDATE= "UPDATE imagen SET " +
      "foto = ? , nombre = ?, alt = ? " +
      "  WHERE id = ? " ;

  private static  final String  SQL_DELETE= "DELETE FROM imagen  WHERE id = ? " ;
  private static  final String  SQL_GETALL= "SELECT * FROM imagen ORDER BY id" ;
  private  static final String  SQL_GETBYID= "SELECT * FROM imagen WHERE id = ? " ;
  private static final String  SQL_EXISTS_BY_FOTO= "SELECT id FROM imagen WHERE foto = ? LIMIT 1" ;

   @Override
  public List<Imagen> getAll() throws SQLException {
    List<Imagen> listaImagenes = new java.util.ArrayList<>();

    try (Connection conn = AdmConexiones.INSTANCE.obtenerConexion();
         PreparedStatement pst = conn.prepareStatement(SQL_GETALL);
         ResultSet rs = pst.executeQuery()) {

      while (rs.next()) {
        Imagen imagen = new Imagen();
        imagen.setId(rs.getInt("id"));
        imagen.setFoto(rs.getString("foto"));
        imagen.setNombre(rs.getString("nombre"));
        imagen.setAlt(rs.getString("alt"));

        listaImagenes.add(imagen);
      }

    } catch (SQLException e) {
        System.out.println("Error al obtener imágenes: " + e.getMessage());
        throw new RuntimeException(e);
    }

     return listaImagenes;
}


    @Override
    public void insert(Imagen imagen) throws SQLException {
        try (Connection conn = AdmConexiones.INSTANCE.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

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
           throw new RuntimeException(e);
        }
    }


      @Override
       public void update(Imagen objeto) throws SQLException {
        Imagen imagen = objeto;
        // solo si la imagen existe la modifico
        if (this.existsById(imagen.getId())) {
            try (Connection conn = AdmConexiones.INSTANCE.obtenerConexion();
                 PreparedStatement pst = conn.prepareStatement(SQL_UPDATE)) {

                pst.setString(1, imagen.getFoto());
                pst.setString(2, imagen.getNombre());
                pst.setString(3, imagen.getAlt());

                int resultado = pst.executeUpdate();
                if (resultado == 1) {
                    System.out.println("La imágen se actualizó correctamente");
                } else {
                    System.out.println("No se pudo actualizar la imágen");
                }

            } catch (SQLException e) {
                System.out.println("Error al actualizar la imagen: " + e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    @Override
      public void delete(Integer id) throws SQLException {
        try (Connection conn = AdmConexiones.INSTANCE.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(SQL_DELETE)) {

            pst.setInt(1, id);
            int resultado = pst.executeUpdate();
            if (resultado == 1) {
                System.out.println("Imágen eliminada correctamente");
            } else {
                System.out.println("No se pudo eliminar la imágen");
            }

        } catch (SQLException e) {
            System.out.println("No se pudo eliminar la imágen. Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    @Override
    public Imagen getById(Integer id) throws SQLException {
        Imagen imagen = null;

        try (Connection conn = AdmConexiones.INSTANCE.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(SQL_GETBYID)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    imagen = new Imagen();
                    imagen.setId(rs.getInt("id"));
                    imagen.setFoto(rs.getString("foto"));
                    imagen.setNombre(rs.getString("nombre"));
                    imagen.setAlt(rs.getString("alt"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return imagen;
    }


    @Override
    public boolean existsById(Integer id) throws SQLException {
        boolean existe = false;

        try (Connection conn = AdmConexiones.INSTANCE.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(SQL_GETBYID)) {

            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    existe = true;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return existe;
    }

    public boolean existsByFoto(String foto) throws SQLException {
        boolean existe = false;

        try (Connection conn = AdmConexiones.INSTANCE.obtenerConexion();
             PreparedStatement pst = conn.prepareStatement(SQL_EXISTS_BY_FOTO)) {

            pst.setString(1, foto);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    existe = true;
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return existe;
    }

}

          


  

  
          
        

      

      
