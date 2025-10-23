package org.progl.servlets;

import java.io.IOException;

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
        session.invalidate(); 
}
        res.sendRedirect("inicio");
    }

}
