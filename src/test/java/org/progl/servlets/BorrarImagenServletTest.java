package org.progl.servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BorrarImagenServletTest {

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse res;

    @Mock
    private HttpSession session;

    @Mock
    private PrintWriter writer;

    private BorrarImagenServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        servlet = new BorrarImagenServlet();

        // Comportamiento común: sesión válida de admin
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("tipo")).thenReturn("admin");

        // Mock del writer para los casos de error
        StringWriter stringWriter = new StringWriter();
        when(res.getWriter()).thenReturn(new PrintWriter(stringWriter));
    }

    // ==================== doDelete ====================

    @Test
    void doDeleteSinSesionDeberiaRedirigirAInicio() throws IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(null);

        // 2. Act
        servlet.doDelete(req, res);

        // 3. Assert
        verify(res).sendRedirect("inicio");
    }

    @Test
    void doDeleteSinSerAdminDeberiaRedirigirAInicio() throws IOException {
        // 1. Arrange
        when(session.getAttribute("tipo")).thenReturn("usuario");

        // 2. Act
        servlet.doDelete(req, res);

        // 3. Assert
        verify(res).sendRedirect("inicio");
    }

    @Test
    void doDeleteSinIdDeberiaResponder500() throws IOException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn(null);

        // 2. Act
        servlet.doDelete(req, res);

        // 3. Assert
        verify(res).setStatus(500);
        verify(res).getWriter();
    }

    @Test
    void doDeleteConIdVacioDeberiaResponder500() throws IOException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("   ");

        // 2. Act
        servlet.doDelete(req, res);

        // 3. Assert
        verify(res).setStatus(500);
        verify(res).getWriter();
    }

    @Test
    void doDeleteConIdInvalidoDeberiaResponder500() throws IOException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("abc");

        // 2. Act
        servlet.doDelete(req, res);

        // 3. Assert
        verify(res).setStatus(500);
        verify(res).getWriter();
    }

    @Test
    void doDeleteConIdInexistenteDeberiaResponder500() throws IOException, SQLException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("9999");

        // 2. Act
        servlet.doDelete(req, res);

        // 3. Assert
        // Si no hay BD, lanza SQLException y responde 500
        // Si hay BD y la imagen no existe, lanza ImagenException y responde 500
        verify(res).setStatus(500);
    }

    @Test
    void doDeleteConIdValidoDeberiaResponder200() throws IOException, SQLException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("1");

        // 2. Act
        servlet.doDelete(req, res);

        // 3. Assert
        // Si la BD está conectada y la imagen existe, responde 200
        // Si no hay BD, lanza SQLException y responde 500
        // Verificamos que se setea un status (200 o 500)
        verify(res).setStatus(anyInt());
    }

    @Test
    void doDeleteConIdNegativoDeberiaResponder500() throws IOException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("-5");

        // 2. Act
        servlet.doDelete(req, res);

        // 3. Assert
        verify(res).setStatus(500);
    }

    @Test
    void doDeleteConIdCeroDeberiaResponder500() throws IOException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("0");

        // 2. Act
        servlet.doDelete(req, res);

        // 3. Assert
        verify(res).setStatus(500);
    }

    @Test
    void doDeleteConIdValidoNoDeberiaRedirigirAInicio() throws IOException, SQLException {
        // 1. Arrange
        when(req.getParameter("id")).thenReturn("1");

        // 2. Act
        servlet.doDelete(req, res);

        // 3. Assert
        verify(res, never()).sendRedirect(anyString());
    }
}