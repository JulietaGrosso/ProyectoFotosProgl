package org.progl.servlets;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.progl.daos.ImagenImpl;
import org.progl.entities.Imagen;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/galeria")
public class GaleriaServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    ImagenImpl imagenImpl = new ImagenImpl();
    List<Imagen> listaImagenes;
    try {
      listaImagenes = imagenImpl.getAll();
    } catch (SQLException e) {
      throw new ServletException("Error al obtener las imágenes de la base de datos.", e);
    }
    req.setAttribute("imagenes", listaImagenes);
    RequestDispatcher rd = req.getRequestDispatcher("/galeria.jsp");
    rd.forward(req, res);
  }
}