package org.progl.servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class ContactoServletTest {

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse res;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    private ContactoServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        servlet = new ContactoServlet();
    }

    // ==================== doGet ====================

    @Test
    void doGetDeberiaHacerForwardAContactarJsp() throws ServletException, IOException {
        // 1. Arrange
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doGetNoDeberiaRedirigir() throws ServletException, IOException {
        // 1. Arrange
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(res, never()).sendRedirect(anyString());
    }

    // ==================== doPost — Sin sesión ====================

    @Test
    void doPostSinSesionDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(null);
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("Debes iniciar sesión para contactar al servicio."));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostSinAtributoLogueadoDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("logueado")).thenReturn(null);
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("Debes iniciar sesión para contactar al servicio."));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostSinSesionNoDeberiaRedirigir() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(null);
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(res, never()).sendRedirect(anyString());
    }

    // ==================== doPost — Con sesión, validaciones ====================

    @Test
    void doPostSinNombreDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("logueado")).thenReturn("true");
        when(req.getParameter("nombre")).thenReturn(null);
        when(req.getParameter("email")).thenReturn("test@test.com");
        when(req.getParameter("telefono")).thenReturn("1234");
        when(req.getParameter("motivo")).thenReturn("Consulta");
        when(req.getParameter("mensaje")).thenReturn("Hola");
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("El nombre y apellido es obligatorio."));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostConNombreVacioDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("logueado")).thenReturn("true");
        when(req.getParameter("nombre")).thenReturn("   ");
        when(req.getParameter("email")).thenReturn("test@test.com");
        when(req.getParameter("telefono")).thenReturn("1234");
        when(req.getParameter("motivo")).thenReturn("Consulta");
        when(req.getParameter("mensaje")).thenReturn("Hola");
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("El nombre y apellido es obligatorio."));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostSinEmailDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("logueado")).thenReturn("true");
        when(req.getParameter("nombre")).thenReturn("Juan Perez");
        when(req.getParameter("email")).thenReturn(null);
        when(req.getParameter("telefono")).thenReturn("1234");
        when(req.getParameter("motivo")).thenReturn("Consulta");
        when(req.getParameter("mensaje")).thenReturn("Hola");
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("El email es obligatorio."));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostSinTelefonoDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("logueado")).thenReturn("true");
        when(req.getParameter("nombre")).thenReturn("Juan Perez");
        when(req.getParameter("email")).thenReturn("test@test.com");
        when(req.getParameter("telefono")).thenReturn(null);
        when(req.getParameter("motivo")).thenReturn("Consulta");
        when(req.getParameter("mensaje")).thenReturn("Hola");
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("El número de teléfono es obligatorio."));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostSinMotivoDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("logueado")).thenReturn("true");
        when(req.getParameter("nombre")).thenReturn("Juan Perez");
        when(req.getParameter("email")).thenReturn("test@test.com");
        when(req.getParameter("telefono")).thenReturn("1234");
        when(req.getParameter("motivo")).thenReturn(null);
        when(req.getParameter("mensaje")).thenReturn("Hola");
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("El motivo de contacto es obligatorio."));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostSinMensajeDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("logueado")).thenReturn("true");
        when(req.getParameter("nombre")).thenReturn("Juan Perez");
        when(req.getParameter("email")).thenReturn("test@test.com");
        when(req.getParameter("telefono")).thenReturn("1234");
        when(req.getParameter("motivo")).thenReturn("Consulta");
        when(req.getParameter("mensaje")).thenReturn(null);
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("El mensaje es obligatorio."));
        verify(dispatcher).forward(req, res);
    }

    // ==================== doPost — Caso exitoso ====================

    @Test
    void doPostConTodosLosCamposValidosDeberiaMostrarExito() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("logueado")).thenReturn("true");
        when(req.getParameter("nombre")).thenReturn("Juan Perez");
        when(req.getParameter("email")).thenReturn("test@test.com");
        when(req.getParameter("telefono")).thenReturn("1234");
        when(req.getParameter("motivo")).thenReturn("Consulta");
        when(req.getParameter("mensaje")).thenReturn("Hola, quiero info");
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeExito"), eq("Mensaje enviado correctamente. ¡Gracias por contactarnos!"));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostConTodosLosCamposValidosNoDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("logueado")).thenReturn("true");
        when(req.getParameter("nombre")).thenReturn("Juan Perez");
        when(req.getParameter("email")).thenReturn("test@test.com");
        when(req.getParameter("telefono")).thenReturn("1234");
        when(req.getParameter("motivo")).thenReturn("Consulta");
        when(req.getParameter("mensaje")).thenReturn("Hola, quiero info");
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req, never()).setAttribute(eq("mensajeError"), anyString());
    }

    @Test
    void doPostConTodosLosCamposValidosNoDeberiaRedirigir() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("logueado")).thenReturn("true");
        when(req.getParameter("nombre")).thenReturn("Juan Perez");
        when(req.getParameter("email")).thenReturn("test@test.com");
        when(req.getParameter("telefono")).thenReturn("1234");
        when(req.getParameter("motivo")).thenReturn("Consulta");
        when(req.getParameter("mensaje")).thenReturn("Hola, quiero info");
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(res, never()).sendRedirect(anyString());
    }

    @Test
    void doPostConNombreSinApellidoDeberiaSerExitoso() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("logueado")).thenReturn("true");
        when(req.getParameter("nombre")).thenReturn("Juan");
        when(req.getParameter("email")).thenReturn("test@test.com");
        when(req.getParameter("telefono")).thenReturn("1234");
        when(req.getParameter("motivo")).thenReturn("Consulta");
        when(req.getParameter("mensaje")).thenReturn("Hola");
        when(req.getRequestDispatcher("/contactar.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeExito"), eq("Mensaje enviado correctamente. ¡Gracias por contactarnos!"));
        verify(dispatcher).forward(req, res);
    }
}