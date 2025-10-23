package org.progl.servlets;

import java.io.IOException;

import org.progl.daos.ImagenImpl;
import org.progl.entities.Imagen;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editar")
public class EditarImagenServlet extends HttpServlet{
    
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
    
        String id = req.getParameter("id");
        
        ImagenImpl imagenImpl = new ImagenImpl();
        Imagen getImagen = imagenImpl.getById(Integer.parseInt(id));
       
        req.setAttribute("id", getImagen.getId());
        req.setAttribute("foto", getImagen.getFoto());
        req.setAttribute("nombre", getImagen.getNombre());
        req.setAttribute("alt", getImagen.getAlt());

        RequestDispatcher rd = req.getRequestDispatcher("/editarImagen.jsp");
        rd.forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
        
        String nombre = req.getParameter("nombre");
        String alt = req.getParameter("alt");
        String id = req.getParameter("id");
      

        ImagenImpl imagenImpl = new ImagenImpl();
        Imagen getImagen = imagenImpl.getById(Integer.parseInt(id));
         getImagen.setAlt(alt);
         getImagen.setNombre(nombre);
         
         imagenImpl.update(getImagen);
         
         
         res.sendRedirect("galeria");

    }

}
