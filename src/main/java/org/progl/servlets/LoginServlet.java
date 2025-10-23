package org.progl.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.progl.daos.LoginImpl;
import org.progl.entities.Cuenta;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet{

public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    req.setAttribute("mensaje", "¡Hola desde el Servlet ProgI IES63!");
    req.setAttribute("fecha", new Date());
    
    RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
    rd.forward(req, res);
  }

  public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
    String correo = req.getParameter("correo");
    String contrasena = req.getParameter("contrasena");

    LoginImpl loginImpl = new LoginImpl();

     Cuenta login = loginImpl.getByEmail(correo);

      if(correo != null && contrasena.equals(login.getContrasena())){
        
           HttpSession session = req.getSession();
          session.setAttribute("logueado", "true");
          res.sendRedirect("inicio");

      }else{
          res.sendRedirect("login");

      }




    
    
  }




}
