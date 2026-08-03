package org.progl.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Date;

import org.progl.daos.ImagenImpl;
import org.progl.entities.Imagen;
import org.progl.exceptions.ImagenException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpSession;

@WebServlet("/subir")
@MultipartConfig
public class SubirImagenServlet extends HttpServlet {
  
public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    HttpSession session = req.getSession(false);
    if (session == null || !"admin".equals(session.getAttribute("tipo"))) {
        res.sendRedirect("inicio");
        return;
    }
    req.setAttribute("mensaje", "¡Hola desde el Servlet ProgI IES63!");
    req.setAttribute("fecha", new Date());
    
    RequestDispatcher rd = req.getRequestDispatcher("/subirImagen.jsp");
    rd.forward(req, res);
  }

   @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        if (session == null || !"admin".equals(session.getAttribute("tipo"))) {
            res.sendRedirect("inicio");
            return;
        }

        // Recibir los campos del formulario
        Part foto = req.getPart("foto");
        String nombre = req.getParameter("nombre");
        String alt = req.getParameter("alt");

        try {
            // Validar que se recibió el archivo
            if (foto == null || foto.getSize() == 0) {
                throw new ImagenException("No se recibió ningún archivo de imagen.");
            }

            // Obtener el nombre original del archivo
            String fileName = Paths.get(foto.getSubmittedFileName()).getFileName().toString();

            // Validar que el nombre del archivo no esté vacío
            if (fileName == null || fileName.trim().isEmpty()) {
                throw new ImagenException("El nombre del archivo no puede estar vacío.");
            }

            // Verificar si ya existe una imagen con ese nombre en la BD
            ImagenImpl imagenImpl = new ImagenImpl();
            boolean existeFoto;
            try {
                existeFoto = imagenImpl.existsByFoto(fileName);
            } catch (SQLException e) {
                throw new ImagenException("Error al verificar la imagen en la base de datos.");
            }
            if (existeFoto) {
                req.setAttribute("mensajeError", "Ya existe una imagen con el nombre: " + fileName);
                RequestDispatcher rd = req.getRequestDispatcher("/subirImagen.jsp");
                rd.forward(req, res);
                return;
            }

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
            } catch (IOException e) {
                throw new ImagenException("Error al guardar la imagen en el servidor.");
            }

            Imagen imagen = new Imagen(0, fileName, nombre, alt);
            try {
                imagenImpl.insert(imagen);
            } catch (SQLException | RuntimeException e) {
                throw new ImagenException("Error al registrar la imagen en la base de datos.");
            }

            res.sendRedirect("galeria");

        } catch (ImagenException e) {
            req.setAttribute("mensajeError", e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("/subirImagen.jsp");
            rd.forward(req, res);
        }
    }
}
