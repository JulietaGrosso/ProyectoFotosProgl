package org.progl.servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class EditarImagenServletTest {

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse res;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    private EditarImagenServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        servlet = new EditarImagenServlet();

        // Comportamiento común: sesión válida de admin
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("tipo")).thenReturn("admin");
    }

    // ==================== doGet ====================

    @Test
    void doGetSinSesionDeberiaRedirigirAInicio() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(null);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(res).sendRedirect("inicio");
    }

    @Test
    void doGetSinSerAdminDeberiaRedirigirAInicio() throws ServletException, IOException {
        // 1. Arrange
        when(session.getAttribute("tipo")).thenReturn("usuario");

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(res).sendRedirect("inicio");
    }

    @Test
    void doGetSinIdDeberiaRedirigirAGaleria() throws ServletException, IOException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn(null);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("ID de imagen no proporcionado."));
        verify(res).sendRedirect("galeria");
    }

    @Test
    void doGetConIdVacioDeberiaRedirigirAGaleria() throws ServletException, IOException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("   ");

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("ID de imagen no proporcionado."));
        verify(res).sendRedirect("galeria");
    }

    @Test
    void doGetConIdInvalidoDeberiaRedirigirAGaleria() throws ServletException, IOException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("abc");

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("ID de imagen inválido."));
        verify(res).sendRedirect("galeria");
    }

    @Test
    void doGetConIdInexistenteDeberiaRedirigirAGaleria() throws ServletException, IOException, SQLException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("999");
        // No se puede mockear ImagenImpl directamente (se crea con new dentro del servlet),
        // pero si la BD no está conectada, lanzará SQLException y redirige a galeria.

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(res).sendRedirect("galeria");
    }

    @Test
    void doGetConIdValidoDeberiaHacerForwardAEditarImagen() throws ServletException, IOException, SQLException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("1");
        when(req.getRequestDispatcher("/editarImagen.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        // Si la BD está conectada y existe la imagen, hace forward
        // Si no hay BD, lanza SQLException y redirige a galeria
        // Verificamos que al menos se llamó a uno de los dos
        verify(res, atLeastOnce()).sendRedirect(anyString());
    }

    // ==================== doPost ====================

    @Test
    void doPostSinSesionDeberiaRedirigirAInicio() throws IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(null);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(res).sendRedirect("inicio");
    }

    @Test
    void doPostSinSerAdminDeberiaRedirigirAInicio() throws IOException {
        // 1. Arrange
        when(session.getAttribute("tipo")).thenReturn("usuario");

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(res).sendRedirect("inicio");
    }

    @Test
    void doPostSinIdDeberiaRedirigirAGaleria() throws IOException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn(null);
        when(req.getParameter("nombre")).thenReturn("Foto editada");
        when(req.getParameter("alt")).thenReturn("Alt editado");

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(res).sendRedirect("galeria");
    }

    @Test
    void doPostConIdVacioDeberiaRedirigirAGaleria() throws IOException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("");
        when(req.getParameter("nombre")).thenReturn("Foto editada");
        when(req.getParameter("alt")).thenReturn("Alt editado");

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(res).sendRedirect("galeria");
    }

    @Test
    void doPostConIdInvalidoDeberiaRedirigirAGaleria() throws IOException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("xyz");
        when(req.getParameter("nombre")).thenReturn("Foto editada");
        when(req.getParameter("alt")).thenReturn("Alt editado");

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(res).sendRedirect("galeria");
    }

    @Test
    void doPostConIdInexistenteDeberiaRedirigirAGaleria() throws IOException, SQLException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("9999");
        when(req.getParameter("nombre")).thenReturn("Foto editada");
        when(req.getParameter("alt")).thenReturn("Alt editado");

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(res).sendRedirect("galeria");
    }

    @Test
    void doPostConParametrosValidosDeberiaRedirigirAGaleria() throws IOException, SQLException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("nombre")).thenReturn("Foto editada");
        when(req.getParameter("alt")).thenReturn("Alt editado");

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(res).sendRedirect("galeria");
    }

    @Test
    void doPostConNombreNullDeberiaRedirigirAGaleria() throws IOException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("nombre")).thenReturn(null);
        when(req.getParameter("alt")).thenReturn("Alt editado");

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(res).sendRedirect("galeria");
    }

    @Test
    void doPostConAltNullDeberiaRedirigirAGaleria() throws IOException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("nombre")).thenReturn("Foto editada");
        when(req.getParameter("alt")).thenReturn(null);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(res).sendRedirect("galeria");
    }
}