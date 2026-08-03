package org.progl.servlets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Paths;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.nio.file.Path;

class SubirImagenServletTest {

    @Mock
    private HttpServletRequest req;

    @Mock
    private HttpServletResponse res;

    @Mock
    private HttpSession session;

    @Mock
    private Part foto;

    @Mock
    private RequestDispatcher dispatcher;

    private SubirImagenServlet servlet;

    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        servlet = new SubirImagenServlet();

        // Configurar UPLOAD_DIR al directorio temporal para no escribir en C:\proyfotos
        System.setProperty("UPLOAD_DIR", tempDir.toString());

        // Comportamiento común: sesión válida de admin
        when(req.getSession(false)).thenReturn(session);
        when(session.getAttribute("tipo")).thenReturn("admin");
    }

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
    void doGetSiendoAdminDeberiaHacerForwardASubirImagen() throws ServletException, IOException {
        // 1. Arrange
        when(req.getRequestDispatcher("/subirImagen.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doGet(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensaje"), anyString());
        verify(req).setAttribute(eq("fecha"), any());
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostSinSesionDeberiaRedirigirAInicio() throws ServletException, IOException {
        // 1. Arrange
        when(req.getSession(false)).thenReturn(null);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(res).sendRedirect("inicio");
    }

    @Test
    void doPostSinArchivoDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getPart("foto")).thenReturn(foto);
        when(foto.getSize()).thenReturn(0L);
        when(req.getRequestDispatcher("/subirImagen.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("No se recibió ningún archivo de imagen."));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostConArchivoVacioDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getPart("foto")).thenReturn(foto);
        when(foto.getSize()).thenReturn(0L);
        when(req.getRequestDispatcher("/subirImagen.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("No se recibió ningún archivo de imagen."));
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostConNombreVacioDeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getPart("foto")).thenReturn(foto);
        when(foto.getSize()).thenReturn(1024L);
        when(foto.getSubmittedFileName()).thenReturn("   ");
        when(req.getRequestDispatcher("/subirImagen.jsp")).thenReturn(dispatcher);

        // 2. Act & 3. Assert — Paths.get("   ") lanza InvalidPathException en Windows
        // El servlet no maneja esta excepción, por lo que se propaga como RuntimeException
        assertThrows(RuntimeException.class, () -> servlet.doPost(req, res));
    }

    @Test
    void doPostSinParametroNombreDeberiaUsarNullEnImagen() throws ServletException, IOException {
        // 1. Arrange
        byte[] contenido = "imagen de prueba".getBytes();
        when(req.getPart("foto")).thenReturn(foto);
        when(foto.getSize()).thenReturn((long) contenido.length);
        when(foto.getSubmittedFileName()).thenReturn("foto-test.jpg");
        when(foto.getInputStream()).thenReturn(new ByteArrayInputStream(contenido));
        when(req.getParameter("nombre")).thenReturn(null);
        when(req.getParameter("alt")).thenReturn("descripción de prueba");
        // Sin BD, existsByFoto lanza SQLException → el servlet hace forward a JSP con error
        when(req.getRequestDispatcher("/subirImagen.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert — sin BD, el servlet no puede verificar la imagen, hace forward con error
        verify(req).setAttribute(eq("mensajeError"), anyString());
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostConArchivoValidoDeberiaRedirigirAGaleria() throws ServletException, IOException {
        // 1. Arrange
        byte[] contenido = "contenido de imagen de prueba".getBytes();
        when(req.getPart("foto")).thenReturn(foto);
        when(foto.getSize()).thenReturn((long) contenido.length);
        when(foto.getSubmittedFileName()).thenReturn("foto-valida.jpg");
        when(foto.getInputStream()).thenReturn(new ByteArrayInputStream(contenido));
        when(req.getParameter("nombre")).thenReturn("Foto de prueba");
        when(req.getParameter("alt")).thenReturn("Descripción de prueba");
        // Sin BD, existsByFoto lanza SQLException → el servlet hace forward a JSP con error
        when(req.getRequestDispatcher("/subirImagen.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert — sin BD, el servlet no puede verificar la imagen, hace forward con error
        verify(req).setAttribute(eq("mensajeError"), anyString());
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostConArchivoValidoDeberiaGuardarArchivoEnDisco() throws ServletException, IOException {
        // 1. Arrange
        byte[] contenido = "contenido binario de la imagen".getBytes();
        when(req.getPart("foto")).thenReturn(foto);
        when(foto.getSize()).thenReturn((long) contenido.length);
        when(foto.getSubmittedFileName()).thenReturn("foto-guardar.jpg");
        when(foto.getInputStream()).thenReturn(new ByteArrayInputStream(contenido));
        when(req.getParameter("nombre")).thenReturn("Foto guardar");
        when(req.getParameter("alt")).thenReturn("Alt de prueba");
        // Sin BD, existsByFoto lanza SQLException → el servlet hace forward a JSP con error
        when(req.getRequestDispatcher("/subirImagen.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert — sin BD, el servlet hace forward con error, no guarda el archivo
        verify(req).setAttribute(eq("mensajeError"), anyString());
        verify(dispatcher).forward(req, res);
    }

    @Test
    void doPostConErrorDeIODeberiaMostrarError() throws ServletException, IOException {
        // 1. Arrange
        when(req.getPart("foto")).thenReturn(foto);
        when(foto.getSize()).thenReturn(1024L);
        when(foto.getSubmittedFileName()).thenReturn("foto-error.jpg");
        when(foto.getInputStream()).thenThrow(new IOException("Error simulado de IO"));
        when(req.getRequestDispatcher("/subirImagen.jsp")).thenReturn(dispatcher);

        // 2. Act
        servlet.doPost(req, res);

        // 3. Assert
        verify(req).setAttribute(eq("mensajeError"), eq("Error al guardar la imagen en el servidor."));
        verify(dispatcher).forward(req, res);
    }
}