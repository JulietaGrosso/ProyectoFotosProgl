package org.progl.servlets;

import org.progl.daos.ImagenImpl;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/borrar")
public class BorrarImagenServlet extends HttpServlet{
    
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res){
        String id = req.getParameter("id");

        ImagenImpl imagenImpl = new ImagenImpl();
        imagenImpl.delete(Integer.parseInt(id));

        res.setStatus(200);

    }






}
