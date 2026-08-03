package org.progl.daos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import org.progl.entities.Imagen;
import org.progl.interfaces.AdmConexiones;

class ImagenImplTest {

    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement pst;

    @Mock
    private ResultSet rs;

    private HikariDataSource originalDataSource;
    private ImagenImpl imagenImpl;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        imagenImpl = new ImagenImpl();

        // Guardar el dataSource original y reemplazarlo con un mock
        Field field = AdmConexiones.class.getDeclaredField("dataSource");
        field.setAccessible(true);
        originalDataSource = (HikariDataSource) field.get(AdmConexiones.INSTANCE);

        HikariDataSource mockDataSource = mock(HikariDataSource.class);
        HikariPoolMXBean mockPoolMXBean = mock(HikariPoolMXBean.class);
        when(mockDataSource.getConnection()).thenReturn(conn);
        when(mockDataSource.getHikariPoolMXBean()).thenReturn(mockPoolMXBean);
        when(mockPoolMXBean.getActiveConnections()).thenReturn(0);
        field.set(AdmConexiones.INSTANCE, mockDataSource);

        // Comportamiento común: prepareStatement devuelve el pst mockeado
        when(conn.prepareStatement(anyString())).thenReturn(pst);
        when(conn.prepareStatement(anyString(), anyInt())).thenReturn(pst);
    }

    @AfterEach
    void tearDown() throws Exception {
        // Restaurar el dataSource original
        Field field = AdmConexiones.class.getDeclaredField("dataSource");
        field.setAccessible(true);
        field.set(AdmConexiones.INSTANCE, originalDataSource);
    }

    // ==================== getAll ====================

    @Test
    void getAllConResultadosDeberiaRetornarLista() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("id")).thenReturn(1, 2);
        when(rs.getString("foto")).thenReturn("foto1.jpg", "foto2.jpg");
        when(rs.getString("nombre")).thenReturn("Nombre 1", "Nombre 2");
        when(rs.getString("alt")).thenReturn("Alt 1", "Alt 2");

        // 2. Act
        List<Imagen> resultado = imagenImpl.getAll();

        // 3. Assert
        assertEquals(2, resultado.size());
        assertEquals(1, resultado.get(0).getId());
        assertEquals("foto1.jpg", resultado.get(0).getFoto());
        assertEquals("Nombre 1", resultado.get(0).getNombre());
        assertEquals("Alt 1", resultado.get(0).getAlt());
        assertEquals(2, resultado.get(1).getId());
        assertEquals("foto2.jpg", resultado.get(1).getFoto());
    }

    @Test
    void getAllSinResultadosDeberiaRetornarListaVacia() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // 2. Act
        List<Imagen> resultado = imagenImpl.getAll();

        // 3. Assert
        assertTrue(resultado.isEmpty());
    }

    @Test
    void getAllConErrorDeSQLDeberiaLanzarRuntimeException() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenThrow(new SQLException("Error de BD"));

        // 2. Act & 3. Assert
        assertThrows(RuntimeException.class, () -> imagenImpl.getAll());
    }

    // ==================== getById ====================

    @Test
    void getByIdExistenteDeberiaRetornarImagen() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("id")).thenReturn(5);
        when(rs.getString("foto")).thenReturn("foto5.jpg");
        when(rs.getString("nombre")).thenReturn("Foto 5");
        when(rs.getString("alt")).thenReturn("Alt 5");

        // 2. Act
        Imagen resultado = imagenImpl.getById(5);

        // 3. Assert
        assertNotNull(resultado);
        assertEquals(5, resultado.getId());
        assertEquals("foto5.jpg", resultado.getFoto());
        assertEquals("Foto 5", resultado.getNombre());
        assertEquals("Alt 5", resultado.getAlt());
    }

    @Test
    void getByIdInexistenteDeberiaRetornarNull() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // 2. Act
        Imagen resultado = imagenImpl.getById(999);

        // 3. Assert
        assertNull(resultado);
    }

    @Test
    void getByIdDeberiaSetearParametroId() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // 2. Act
        imagenImpl.getById(7);

        // 3. Assert
        verify(pst).setInt(1, 7);
    }

    // ==================== existsById ====================

    @Test
    void existsByIdExistenteDeberiaRetornarTrue() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);

        // 2. Act
        boolean resultado = imagenImpl.existsById(1);

        // 3. Assert
        assertTrue(resultado);
    }

    @Test
    void existsByIdInexistenteDeberiaRetornarFalse() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // 2. Act
        boolean resultado = imagenImpl.existsById(999);

        // 3. Assert
        assertFalse(resultado);
    }

    @Test
    void existsByIdDeberiaSetearParametroId() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // 2. Act
        imagenImpl.existsById(3);

        // 3. Assert
        verify(pst).setInt(1, 3);
    }

    // ==================== existsByFoto ====================

    @Test
    void existsByFotoExistenteDeberiaRetornarTrue() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);

        // 2. Act
        boolean resultado = imagenImpl.existsByFoto("foto.jpg");

        // 3. Assert
        assertTrue(resultado);
    }

    @Test
    void existsByFotoInexistenteDeberiaRetornarFalse() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // 2. Act
        boolean resultado = imagenImpl.existsByFoto("no-existe.jpg");

        // 3. Assert
        assertFalse(resultado);
    }

    @Test
    void existsByFotoDeberiaSetearParametroFoto() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // 2. Act
        imagenImpl.existsByFoto("prueba.png");

        // 3. Assert
        verify(pst).setString(1, "prueba.png");
    }

    // ==================== insert ====================

    @Test
    void insertDeberiaSetearParametrosYEjecutar() throws SQLException {
        // 1. Arrange
        Imagen imagen = new Imagen(0, "nueva.jpg", "Nueva imagen", "Alt nueva");
        when(pst.executeUpdate()).thenReturn(1);
        when(pst.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(42);

        // 2. Act
        imagenImpl.insert(imagen);

        // 3. Assert
        verify(pst).setString(1, "nueva.jpg");
        verify(pst).setString(2, "Nueva imagen");
        verify(pst).setString(3, "Alt nueva");
        verify(pst).executeUpdate();
        assertEquals(42, imagen.getId());
    }

    @Test
    void insertConErrorDeSQLDeberiaLanzarRuntimeException() throws SQLException {
        // 1. Arrange
        Imagen imagen = new Imagen(0, "error.jpg", "Error", "Alt error");
        when(pst.executeUpdate()).thenThrow(new SQLException("Error de BD"));

        // 2. Act & 3. Assert
        assertThrows(RuntimeException.class, () -> imagenImpl.insert(imagen));
    }

    // ==================== delete ====================

    @Test
    void deleteDeberiaSetearParametroYEjecutar() throws SQLException {
        // 1. Arrange
        when(pst.executeUpdate()).thenReturn(1);

        // 2. Act
        imagenImpl.delete(5);

        // 3. Assert
        verify(pst).setInt(1, 5);
        verify(pst).executeUpdate();
    }

    @Test
    void deleteConErrorDeSQLDeberiaLanzarRuntimeException() throws SQLException {
        // 1. Arrange
        when(pst.executeUpdate()).thenThrow(new SQLException("Error de BD"));

        // 2. Act & 3. Assert
        assertThrows(RuntimeException.class, () -> imagenImpl.delete(5));
    }

    // ==================== update ====================

    @Test
    void updateDeberiaSetearParametrosYEjecutar() throws SQLException {
        // 1. Arrange
        Imagen imagen = new Imagen(3, "actualizada.jpg", "Actualizada", "Alt actualizado");
        // existsById devuelve true
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        // update ejecuta
        when(pst.executeUpdate()).thenReturn(1);

        // 2. Act
        imagenImpl.update(imagen);

        // 3. Assert
        verify(pst).setString(1, "actualizada.jpg");
        verify(pst).setString(2, "Actualizada");
        verify(pst).setString(3, "Alt actualizado");
        verify(pst).executeUpdate();
    }

    @Test
    void updateConImagenInexistenteNoDeberiaActualizar() throws SQLException {
        // 1. Arrange
        Imagen imagen = new Imagen(999, "no-existe.jpg", "No existe", "Alt");
        // existsById devuelve false
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // 2. Act
        imagenImpl.update(imagen);

        // 3. Assert
        // No debería llamar a executeUpdate del UPDATE (solo del SELECT del existsById)
        verify(pst, never()).setString(1, "no-existe.jpg");
    }
}