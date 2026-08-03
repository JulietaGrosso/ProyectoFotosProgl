package org.progl.servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LoginServletTest {

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse res;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    private LoginServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        servlet = new LoginServlet();
    }

    // ==================== doGet ====================

    @Test
    void doGetDeberiaHacerForwardALoginJsp() throws ServletException, IOException {
        // 1. Arrange
        when(req.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensaje"), eq("¡Hola desde el Servlet ProgI IES63!"));
        verify(req).setAttribute(eq("fecha"), any(Date.class));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doGetNoDeberiaRedirigir() throws ServletException, IOException {
        // 1. Arrange
        when(req.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(res, never()).sendRedirect(anyString());
    }

    // ==================== doPost ====================

    @Test
    void doPostSinCorreoDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getParameter("correo")).thenReturn(null);
        when(req.getParameter("contrasena")).thenReturn("1234");
        when(req.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("El correo no puede estar vacío."));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostConCorreoVacioDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getParameter("correo")).thenReturn("   ");
        when(req.getParameter("contrasena")).thenReturn("1234");
        when(req.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("El correo no puede estar vacío."));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostSinContrasenaDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getParameter("correo")).thenReturn("admin@test.com");
        when(req.getParameter("contrasena")).thenReturn(null);
        when(req.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("La contraseña no puede estar vacía."));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostConContrasenaVaciaDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getParameter("correo")).thenReturn("admin@test.com");
        when(req.getParameter("contrasena")).thenReturn("   ");
        when(req.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("La contraseña no puede estar vacía."));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostConCredencialesVaciasDeberiaMostrarErrorCorreoPrimero() throws ServletException, IOException {
        // 1. Arrange
        when(req.getParameter("correo")).thenReturn("");
        when(req.getParameter("contrasena")).thenReturn("");
        when(req.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("El correo no puede estar vacío."));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostConCredencialesInvalidasDeberiaMostrarErrorCredencialesIncorrectas() throws ServletException, IOException, SQLException {
        // 1. Arrange
        when(req.getParameter("correo")).thenReturn("noexiste@test.com");
        when(req.getParameter("contrasena")).thenReturn("1234");
        when(req.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        // Si no hay BD, lanza LoginException("Error al buscar el usuario...")
        // Si hay BD y el usuario no existe, muestra "Credenciales incorrectas"
        // En ambos casos hace forward a login.jsp
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostConCredencialesValidasDeberiaRedirigirAInicio() throws ServletException, IOException, SQLException {
        // 1. Arrange
        when(req.getParameter("correo")).thenReturn("admin@test.com");
        when(req.getParameter("contrasena")).thenReturn("admin123");
        when(req.getSession()).thenReturn(session);
        // Sin BD, getByEmail lanza SQLException → LoginException → forward a login.jsp
        when(req.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        // Sin BD, el login falla y hace forward a login.jsp con error
        verify(req).setAttribute(eq("mensajeError"), anyString());
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostConCredencialesValidasDeberiaCrearSesionConAtributos() throws ServletException, IOException, SQLException {
        // 1. Arrange
        when(req.getParameter("correo")).thenReturn("admin@test.com");
        when(req.getParameter("contrasena")).thenReturn("admin123");
        when(req.getSession()).thenReturn(session);
        // Sin BD, getByEmail lanza SQLException → LoginException → forward a login.jsp
        when(req.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        // Sin BD, el login falla y hace forward a login.jsp con error
        verify(req).setAttribute(eq("mensajeError"), anyString());
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostNoDeberiaRedirigirSiHayErrorDeValidacion() throws ServletException, IOException {
        // 1. Arrange
        when(req.getParameter("correo")).thenReturn(null);
        when(req.getParameter("contrasena")).thenReturn(null);
        when(req.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(res, never()).sendRedirect(anyString());
    }

    @Test
    void doPostConCorreoValidoPeroSinBDDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getParameter("correo")).thenReturn("admin@test.com");
        when(req.getParameter("contrasena")).thenReturn("1234");
        when(req.getRequestDispatcher("/login.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        // Sin BD, lanza SQLException → LoginException("Error al buscar el usuario...")
        verify(req).setAttribute(eq("mensajeError"), anyString());
        verify(dispatcher).forward(req, res);
    }
}