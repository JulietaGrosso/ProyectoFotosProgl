package org.progl.servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.nio.file.Path;

class MostrarFotoServletTest {

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse res;

    @Mock
    private ServletContext servletContext;

    private MostrarFotoServlet servlet;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        servlet = new MostrarFotoServlet();

        // El servlet usa System.getenv("UPLOAD_DIR") primero, y si es null usa
        // System.getProperty("catalina.base") + "/uploads".
        // Como no podemos setear env vars en runtime, configuramos catalina.base
        // al directorio temporal y creamos un subdirectorio "uploads" dentro.
        Path uploadsDir = tempDir.resolve("uploads");
        Files.createDirectories(uploadsDir);
        System.setProperty("catalina.base", tempDir.toString());
    }

    @Test
    void doGetSinParametroFotoDeberiaResponder400() throws IOException, ServletException {
        // 1. Arrange
        when(req.getParameter("foto")).thenReturn(null);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(res).sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'foto'");
    }

    @Test
    void doGetConParametroFotoVacioDeberiaResponder400() throws IOException, ServletException {
        // 1. Arrange
        when(req.getParameter("foto")).thenReturn("");

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(res).sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'foto'");
    }

    @Test
    void doGetConFotoInexistenteDeberiaResponder404() throws IOException, ServletException {
        // 1. Arrange
        when(req.getParameter("foto")).thenReturn("no-existe.jpg");

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(res).sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró la imagen: no-existe.jpg");
    }

   
  
    @Test
    void doGetSinParametroFotoNoDeberiaSetearContentType() throws IOException, ServletException {
        // 1. Arrange
        when(req.getParameter("foto")).thenReturn(null);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(res, never()).setContentType(anyString());
    }

    @Test
    void doGetConFotoInexistenteNoDeberiaSetearContentType() throws IOException, ServletException {
        // 1. Arrange
        when(req.getParameter("foto")).thenReturn("no-existe.jpg");

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(res, never()).setContentType(anyString());
    }

    /**
     * Crea un Answer que delega las escrituras a un ByteArrayOutputStream real,
     * para poder capturar el contenido que el servlet escribe al response.
     */
    private Answer<ServletOutputStream> answerDelegado(ByteArrayOutputStream baos) {
        return invocation -> new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                baos.write(b);
            }

            @Override
            public boolean isReady() {
              // TODO Auto-generated method stub
              throw new UnsupportedOperationException("Unimplemented method 'isReady'");
            }

            @Override
            public void setWriteListener(WriteListener writeListener) {
              // TODO Auto-generated method stub
              throw new UnsupportedOperationException("Unimplemented method 'setWriteListener'");
            }
        };
    }
}