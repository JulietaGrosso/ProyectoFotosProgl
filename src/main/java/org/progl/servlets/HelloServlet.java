package org.progl.servlets;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.progl.daos.ImagenImpl;
import org.progl.entities.Imagen;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/inicio")
public class HelloServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    List<List<Imagen>> listasCarrusel = new ArrayList<>();


     ImagenImpl imagenImpl = new ImagenImpl();
    List<Imagen> listaImagenes;
    try {
      listaImagenes = imagenImpl.getAll();
    } catch (SQLException e) {
      throw new ServletException("Error al obtener las imágenes de la base de datos.", e);
    }

    List<Imagen> listaTemporal = new ArrayList<>();
    for(int i = 0; listaImagenes.size() > i; i++){
      listaTemporal.add(listaImagenes.get(i));

      if (listaTemporal.size() == 3) {
        listasCarrusel.add(listaTemporal);
        listaTemporal = new ArrayList<>();
      }

    }

    if(listaTemporal.size() > 0 ){
        listasCarrusel.add(listaTemporal);
    }
    req.setAttribute("imagenes", listasCarrusel);
    
    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
    rd.forward(req, res);
  }
}