package org.progl.servlets;

import java.io.IOException;

import org.progl.exceptions.ContactarException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/contactar")
public class ContactoServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/contactar.jsp");
        rd.forward(req, res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("logueado") == null) {
            req.setAttribute("mensajeError", "Debes iniciar sesión para contactar al servicio.");
            RequestDispatcher rd = req.getRequestDispatcher("/contactar.jsp");
            rd.forward(req, res);
            return;
        }

        String nombreCompleto = req.getParameter("nombre");
        String email = req.getParameter("email");
        String telefono = req.getParameter("telefono");
        String motivo = req.getParameter("motivo");
        String mensaje = req.getParameter("mensaje");

        try {
            if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
                throw new ContactarException("El nombre y apellido es obligatorio.");
            }
            if (email == null || email.trim().isEmpty()) {
                throw new ContactarException("El email es obligatorio.");
            }
            if (telefono == null || telefono.trim().isEmpty()) {
                throw new ContactarException("El número de teléfono es obligatorio.");
            }
            if (motivo == null || motivo.trim().isEmpty()) {
                throw new ContactarException("El motivo de contacto es obligatorio.");
            }
            if (mensaje == null || mensaje.trim().isEmpty()) {
                throw new ContactarException("El mensaje es obligatorio.");
            }

            String nombre = nombreCompleto.trim();
            String apellido = "";
            int primerEspacio = nombre.indexOf(' ');
            if (primerEspacio > 0) {
                apellido = nombre.substring(primerEspacio + 1).trim();
                nombre = nombre.substring(0, primerEspacio).trim();
            }

            System.out.println("=== Nuevo mensaje de contacto ===");
            System.out.println("Nombre: " + nombre);
            System.out.println("Apellido: " + apellido);
            System.out.println("Email: " + email);
            System.out.println("Teléfono: " + telefono);
            System.out.println("Motivo: " + motivo);
            System.out.println("Mensaje: " + mensaje);
            System.out.println("=================================");

            req.setAttribute("mensajeExito", "Mensaje enviado correctamente. ¡Gracias por contactarnos!");
            RequestDispatcher rd = req.getRequestDispatcher("/contactar.jsp");
            rd.forward(req, res);

        } catch (ContactarException e) {
            req.setAttribute("mensajeError", e.getMessage());
            RequestDispatcher rd = req.getRequestDispatcher("/contactar.jsp");
            rd.forward(req, res);
        }
    }
}