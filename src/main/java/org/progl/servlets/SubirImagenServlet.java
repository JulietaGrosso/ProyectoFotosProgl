package org.progl.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Date;

import org.progl.daos.ImagenImpl;
import org.progl.entities.Imagen;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/subir")
@MultipartConfig
public class SubirImagenServlet extends HttpServlet {
  
public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    req.setAttribute("mensaje", "¡Hola desde el Servlet ProgI IES63!");
    req.setAttribute("fecha", new Date());
    
    RequestDispatcher rd = req.getRequestDispatcher("/subirImagen.jsp");
    rd.forward(req, res);
  }

   @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        // Recibir los campos del formulario
        Part foto = req.getPart("foto");
        String nombre = req.getParameter("nombre");
        String alt = req.getParameter("alt");

        // Obtener el nombre original del archivo
        String fileName = Paths.get(foto.getSubmittedFileName()).getFileName().toString();

        // Carpeta donde se guardarán las imágenes
        String uploadPath = "C:\\proyfotos";

        // Crear la carpeta si no existe
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        // Guardar la imagen físicamente
        try (InputStream input = foto.getInputStream()) {
            File file = new File(uploadPath, fileName);
            try (FileOutputStream output = new FileOutputStream(file)) {
                byte[] buffer = new byte[1024];
                int bytesLeidos;
                while ((bytesLeidos = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesLeidos);
                }
            }
        }


          Imagen imagen = new Imagen(0, fileName, nombre, alt);
          ImagenImpl imagenImpl = new ImagenImpl();
          imagenImpl.insert(imagen);
          res.sendRedirect("galeria");
    }
}
