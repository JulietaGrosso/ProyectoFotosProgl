package org.progl.entities;

public class Imagen {
  private int id;
  private String foto;

  public Imagen(int id, String foto) {
    this.id = id;
    this.foto = foto;
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


  
}
