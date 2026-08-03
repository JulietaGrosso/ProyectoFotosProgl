package org.progl.daos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;

import org.progl.entities.Cuenta;
import org.progl.interfaces.AdmConexiones;

class LoginImplTest {

    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement pst;

    @Mock
    private ResultSet rs;

    private HikariDataSource originalDataSource;
    private LoginImpl loginImpl;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        loginImpl = new LoginImpl();

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
    }

    @AfterEach
    void tearDown() throws Exception {
        // Restaurar el dataSource original
        Field field = AdmConexiones.class.getDeclaredField("dataSource");
        field.setAccessible(true);
        field.set(AdmConexiones.INSTANCE, originalDataSource);
    }

    @Test
    void getByEmailExistenteDeberiaRetornarCuenta() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("nombre")).thenReturn("Admin");
        when(rs.getString("correo")).thenReturn("admin@test.com");
        when(rs.getString("contrasena")).thenReturn("$2a$10$hashedpassword");
        when(rs.getString("tipo")).thenReturn("admin");

        // 2. Act
        Cuenta resultado = loginImpl.getByEmail("admin@test.com");

        // 3. Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Admin", resultado.getNombre());
        assertEquals("admin@test.com", resultado.getCorreo());
        assertEquals("$2a$10$hashedpassword", resultado.getContrasena());
        assertEquals("admin", resultado.getTipo());
    }

    @Test
    void getByEmailInexistenteDeberiaRetornarNull() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // 2. Act
        Cuenta resultado = loginImpl.getByEmail("noexiste@test.com");

        // 3. Assert
        assertNull(resultado);
    }

    @Test
    void getByEmailDeberiaSetearParametroCorreo() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // 2. Act
        loginImpl.getByEmail("test@test.com");

        // 3. Assert
        verify(pst).setString(1, "test@test.com");
    }

    @Test
    void getByEmailConErrorDeSQLDeberiaLanzarRuntimeException() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenThrow(new SQLException("Error de BD"));

        // 2. Act & 3. Assert
        assertThrows(RuntimeException.class, () -> loginImpl.getByEmail("test@test.com"));
    }

    @Test
    void getByEmailConCorreoNullDeberiaSetearNullEnParametro() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // 2. Act
        loginImpl.getByEmail(null);

        // 3. Assert
        verify(pst).setString(1, null);
    }

    @Test
    void getByEmailConCorreoVacioDeberiaSetearVacioEnParametro() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        // 2. Act
        loginImpl.getByEmail("");

        // 3. Assert
        verify(pst).setString(1, "");
    }

    @Test
    void getByEmailConCuentaDeTipoUsuarioDeberiaRetornarTipoUsuario() throws SQLException {
        // 1. Arrange
        when(pst.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("id")).thenReturn(2);
        when(rs.getString("nombre")).thenReturn("Usuario");
        when(rs.getString("correo")).thenReturn("user@test.com");
        when(rs.getString("contrasena")).thenReturn("$2a$10$hasheduser");
        when(rs.getString("tipo")).thenReturn("usuario");

        // 2. Act
        Cuenta resultado = loginImpl.getByEmail("user@test.com");

        // 3. Assert
        assertNotNull(resultado);
        assertEquals("usuario", resultado.getTipo());
    }

    // ==================== Métodos no implementados ====================

    @Test
    void getAllDeberiaLanzarUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> loginImpl.getAll());
    }

    @Test
    void insertDeberiaLanzarUnsupportedOperationException() {
        Cuenta cuenta = new Cuenta();
        assertThrows(UnsupportedOperationException.class, () -> loginImpl.insert(cuenta));
    }

    @Test
    void updateDeberiaLanzarUnsupportedOperationException() {
        Cuenta cuenta = new Cuenta();
        assertThrows(UnsupportedOperationException.class, () -> loginImpl.update(cuenta));
    }

    @Test
    void deleteDeberiaLanzarUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> loginImpl.delete("1"));
    }

    @Test
    void getByIdDeberiaLanzarUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> loginImpl.getById("1"));
    }

    @Test
    void existsByIdDeberiaLanzarUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> loginImpl.existsById("1"));
    }
}