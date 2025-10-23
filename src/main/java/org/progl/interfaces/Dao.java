package org.progl.interfaces;

import java.util.List;

public interface Dao<O,K>{
// O objeto K key clave primaria

  public List<O> getAll();
  public void insert(O objeto);
  public void update(O objeto);
  public void delete(K id);
  public O getById(K id);
  public boolean existsById(K id);

}
