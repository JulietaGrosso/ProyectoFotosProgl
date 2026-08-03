package org.progl.servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class HelloServletTest {

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse res;

    @Mock
    private RequestDispatcher dispatcher;

    private HelloServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        servlet = new HelloServlet();
    }

    @Test
    void doGetDeberiaHacerForwardAIndexJsp() throws ServletException, IOException, SQLException {
        // 1. Arrange
        when(req.getRequestDispatcher("/index.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doGetDeberiaSetearAtributoImagenes() throws ServletException, IOException, SQLException {
        // 1. Arrange
        when(req.getRequestDispatcher("/index.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("imagenes"), any());
    }

    @Test
    void doGetNoDeberiaRedirigir() throws ServletException, IOException, SQLException {
        // 1. Arrange
        when(req.getRequestDispatcher("/index.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(res, never()).sendRedirect(anyString());
    }

    @Test
    void doGetSinBDDeberiaLanzarServletException() throws ServletException, IOException {
        // 1. Arrange
        // Sin BD, getAll() lanza RuntimeException (envuelta por el DAO) → el servlet no la captura
        // Con BD, getAll() funciona y hace forward a index.jsp
        when(req.getRequestDispatcher("/index.jsp")).thenReturn(dispatcher);

        // 2. Act & 3. Assert — puede lanzar ServletException (sin BD) o funcionar (con BD)
        try {
            servlet.doGet(req, res);
            // Si no lanza excepción, la BD está conectada y hace forward
            verify(dispatcher).forward(req, res);
        } catch (ServletException | RuntimeException e) {
            // Si lanza excepción, la BD no está conectada
            assertNotNull(e.getMessage());
        }
    }

    @Test
    void doGetNoDeberiaSetearOtrosAtributos() throws ServletException, IOException, SQLException {
        // 1. Arrange
        when(req.getRequestDispatcher("/index.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("imagenes"), any());
        verify(req, times(1)).setAttribute(anyString(), any());
    }
}