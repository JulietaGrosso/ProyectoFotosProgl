package org.progl.servlets;

import java.io.IOException;
import java.sql.SQLException;

import org.progl.daos.ImagenImpl;
import org.progl.exceptions.ImagenException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/borrar")
public class BorrarImagenServlet extends HttpServlet{
    
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse res) throws IOException {
        HttpSession session = req.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("tipo"))) {
            res.sendRedirect("inicio");
            return;
        }

        try {
            String id = req.getParameter("id");

            if (id == null || id.trim().isEmpty()) {
                throw new ImagenException("ID de imagen no proporcionado.");
            }

            int idNum;
            try {
                idNum = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                throw new ImagenException("ID de imagen inválido.");
            }

            ImagenImpl imagenImpl = new ImagenImpl();

            if (!imagenImpl.existsById(idNum)) {
                throw new ImagenException("La imagen a eliminar no existe.");
            }

            try {
                imagenImpl.delete(idNum);
            } catch (RuntimeException e) {
                throw new ImagenException("Error al eliminar la imagen de la base de datos.");
            }

            res.setStatus(200);

        } catch (ImagenException e) {
            res.setStatus(500);
            res.getWriter().write(e.getMessage());
        } catch (SQLException e1) {
             res.setStatus(500);
             res.getWriter().write(e1.getMessage());
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }






}
