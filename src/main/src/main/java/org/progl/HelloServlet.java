package org.progl;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;

public class HelloServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

    req.setAttribute("mensaje", "¡Hola desde el Servlet ProgI IES63!");
    req.setAttribute("fecha", new Date());
    RequestDispatcher rd = req.getRequestDispatcher("/hello.jsp");
    rd.forward(req, res);
  }
}