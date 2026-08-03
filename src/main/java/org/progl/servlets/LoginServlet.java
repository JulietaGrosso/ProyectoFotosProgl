package org.progl.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import org.progl.daos.LoginImpl;
import org.progl.entities.Cuenta;
import org.progl.exceptions.LoginException;
import utils.PasswordUtils;

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

  public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
    String correo = req.getParameter("correo");
    String contrasena = req.getParameter("contrasena");

    try {
        if (correo == null || correo.trim().isEmpty()) {
            throw new LoginException("El correo no puede estar vacío.");
        }
        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new LoginException("La contraseña no puede estar vacía.");
        }

        LoginImpl loginImpl = new LoginImpl();
        Cuenta login;
        try {
            login = loginImpl.getByEmail(correo);
        } catch (SQLException | RuntimeException e) {
            throw new LoginException("Error al buscar el usuario en la base de datos.");
        }

        if (login != null && PasswordUtils.verifyPassword(contrasena, login.getContrasena())) {

            HttpSession session = req.getSession();
            session.setAttribute("logueado", "true");
            session.setAttribute("tipo", login.getTipo());
            res.sendRedirect("inicio");

        } else {
            req.setAttribute("mensajeError", "Credenciales incorrectas");
            RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
            rd.forward(req, res);
        }

    } catch (LoginException e) {
        req.setAttribute("mensajeError", e.getMessage());
        RequestDispatcher rd = req.getRequestDispatcher("/login.jsp");
        rd.forward(req, res);
    }
  }




}
