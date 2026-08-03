package org.progl.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface Dao<O,K>{
// O objeto K key clave primaria

  public List<O> getAll() throws SQLException;
  public void insert(O objeto) throws SQLException;
  public void update(O objeto) throws SQLException;
  public void delete(K id) throws SQLException;
  public O getById(K id) throws SQLException;
  public boolean existsById(K id) throws SQLException;

}
