package org.progl.servlets;

import java.io.IOException;

import org.progl.exceptions.LoginException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet{

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{

        HttpSession session = req.getSession(false); 
        if (session != null) {
            try {
                session.invalidate(); 
            } catch (IllegalStateException e) {
                throw new LoginException("Error al cerrar la sesión.");
            }
        }
        res.sendRedirect("inicio");
    }

}
