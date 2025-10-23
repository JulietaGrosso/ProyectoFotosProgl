package org.progl.entities;

public class Imagen {
  private int id;
  private String foto;
  private String nombre;
  private String alt;

  public Imagen(){
    
  }


  public Imagen(int id, String foto, String nombre, String alt) {
    this.id = id;
    this.foto = foto;
    this.nombre = nombre;
    this.alt = alt;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFoto() {
    return foto;
  }

  public void setFoto(String foto) {
    this.foto = foto;
  }

 public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public String getAlt() {
    return alt;
  }

  public void setAlt(String alt) {
    this.alt = alt;
  }


}
