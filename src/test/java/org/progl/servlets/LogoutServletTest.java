package org.progl.servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class LogoutServletTest {

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse res;

    @Mock
    private HttpSession session;

    private LogoutServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        servlet = new LogoutServlet();
    }

    @Test
    void doGetConSesionActivaDeberiaInvalidarYRedirigir() throws IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(session).invalidate();
        verify(res).sendRedirect("inicio");
    }

    @Test
    void doGetSinSesionDeberiaRedirigirSinInvalidar() throws IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(null);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(session, never()).invalidate();
        verify(res).sendRedirect("inicio");
    }

    @Test
    void doGetSiempreDeberiaRedirigirAInicio() throws IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(res).sendRedirect("inicio");
    }

    @Test
    void doGetSinSesionNoDeberiaInvalidar() throws IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(null);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(session, never()).invalidate();
    }

    @Test
    void doGetConSesionInvalidaDeberiaLanzarLoginException() throws IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);
        doThrow(new IllegalStateException("Sesión ya invalidada"))
            .when(session).invalidate();

        // 2. Act & 3. Assert
        assertThrows(RuntimeException.class, () -> {
            servlet.doGet(req, res);
        });
    }

    @Test
    void doGetConSesionInvalidaNoDeberiaRedirigir() throws IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(session);
        doThrow(new IllegalStateException("Sesión ya invalidada"))
            .when(session).invalidate();

        // 2. Act & 3. Assert
        assertThrows(RuntimeException.class, () -> {
            servlet.doGet(req, res);
        });
        verify(res, never()).sendRedirect(anyString());
    }
}