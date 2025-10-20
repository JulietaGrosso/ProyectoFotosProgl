package org.progl.servlets;


import java.io.IOException;
import java.util.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/galeria")
public class GaleriaServlet extends HttpServlet {

  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    req.setAttribute("mensaje", "¡Hola desde el Servlet ProgI IES63!");
    req.setAttribute("fecha", new Date());
    RequestDispatcher rd = req.getRequestDispatcher("/galeria.jsp");
    rd.forward(req, res);
  }
}