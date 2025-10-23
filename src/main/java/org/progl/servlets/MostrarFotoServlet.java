package org.progl.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/mostrarFoto")
public class MostrarFotoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //  Obtener el nombre del archivo desde la URL
        String nombreArchivo = request.getParameter("foto");
        if (nombreArchivo == null || nombreArchivo.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'foto'");
            return;
        }

        //  Ruta absoluta donde se guardan las fotos
        String uploadPath = "C:\\proyfotos";
        File archivo = new File(uploadPath, nombreArchivo);

        // Verificar que el archivo exista
        if (!archivo.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró la imagen: " + nombreArchivo);
            return;
        }

        //  Determinar el tipo MIME (para que el navegador sepa cómo mostrarla)
        String mimeType = getServletContext().getMimeType(archivo.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setContentLengthLong(archivo.length());

        //  Enviar el archivo al navegador
        try (FileInputStream in = new FileInputStream(archivo);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesLeidos;
            while ((bytesLeidos = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesLeidos);
            }
        }
    }
}
