package org.progl.servlets;

import java.io.IOException;
import java.sql.SQLException;

import org.progl.daos.ImagenImpl;
import org.progl.entities.Imagen;
import org.progl.exceptions.ImagenException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/editar")
public class EditarImagenServlet extends HttpServlet{
    
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
    
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
            Imagen getImagen = imagenImpl.getById(idNum);

            if (getImagen == null) {
                throw new ImagenException("La imagen solicitada no existe.");
            }

            req.setAttribute("id", getImagen.getId());
            req.setAttribute("foto", getImagen.getFoto());
            req.setAttribute("nombre", getImagen.getNombre());
            req.setAttribute("alt", getImagen.getAlt());

            RequestDispatcher rd = req.getRequestDispatcher("/editarImagen.jsp");
            rd.forward(req, res);

        } catch (ImagenException e) {
            req.setAttribute("mensajeError", e.getMessage());
            res.sendRedirect("galeria");
        } catch (SQLException e) {
              req.setAttribute("mensajeError", e.getMessage());
              res.sendRedirect("galeria");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        
        HttpSession session = req.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("tipo"))) {
            res.sendRedirect("inicio");
            return;
        }

        try {
            String nombre = req.getParameter("nombre");
            String alt = req.getParameter("alt");
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
            Imagen getImagen = imagenImpl.getById(idNum);

            if (getImagen == null) {
                throw new ImagenException("La imagen solicitada no existe.");
            }

            getImagen.setAlt(alt);
            getImagen.setNombre(nombre);

            try {
                imagenImpl.update(getImagen);
            } catch (RuntimeException e) {
                throw new ImagenException("Error al actualizar la imagen en la base de datos.");
            }

            res.sendRedirect("galeria");

        } catch (ImagenException e) {
             e.printStackTrace();
            res.sendRedirect("galeria");
        } catch (SQLException e1) {
            res.sendRedirect("galeria");
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

}
