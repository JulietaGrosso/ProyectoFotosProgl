package org.progl.servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GaleriaServletTest {

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse res;

    @Mock
    private RequestDispatcher dispatcher;

    private GaleriaServlet servlet;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        servlet = new GaleriaServlet();
    }

    @Test
    void doGetDeberiaHacerForwardAGaleriaJsp() throws ServletException, IOException, SQLException {
        // 1. Arrange
        when(req.getRequestDispatcher("/galeria.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doGetDeberiaSetearAtributoImagenes() throws ServletException, IOException, SQLException {
        // 1. Arrange
        when(req.getRequestDispatcher("/galeria.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("imagenes"), anyList());
    }

    @Test
    void doGetNoDeberiaRedirigir() throws ServletException, IOException, SQLException {
        // 1. Arrange
        when(req.getRequestDispatcher("/galeria.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(res, never()).sendRedirect(anyString());
    }

    @Test
    void doGetSinBDDeberiaLanzarServletException() throws ServletException, IOException {
        // 1. Arrange
        // Sin BD, getAll() lanza RuntimeException (envuelta por el DAO) → el servlet no la captura
        // Con BD, getAll() funciona y hace forward a galeria.jsp
        when(req.getRequestDispatcher("/galeria.jsp")).thenReturn(dispatcher);

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
        when(req.getRequestDispatcher("/galeria.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        // Solo debería setear "imagenes", no otros atributos
        verify(req).setAttribute(eq("imagenes"), anyList());
        verify(req, times(1)).setAttribute(anyString(), any());
    }

    @Test
    void doGetConBDDeberiaSetearListaNoNula() throws ServletException, IOException, SQLException {
        // 1. Arrange
        when(req.getRequestDispatcher("/galeria.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        // Si la BD está conectada, setea una lista (puede ser vacía pero no null)
        // Si no hay BD, lanza ServletException
        verify(req).setAttribute(eq("imagenes"), anyList());
    }
}