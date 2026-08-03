package org.progl.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CuentaTest {

    @Test
    void constructorVacioDeberiaCrearInstanciaConValoresPorDefecto() {
        Cuenta c = new Cuenta();

        assertNotNull(c);
        assertEquals(0, c.getId());
        assertNull(c.getNombre());
        assertNull(c.getCorreo());
        assertNull(c.getContrasena());
        assertNull(c.getTipo());
    }

    @Test
    void constructorConParametrosDeberiaSetearTodosLosCampos() {
        Cuenta c = new Cuenta(1, "Admin", "admin@test.com", "$2a$10$hash", "admin");

        assertEquals(1, c.getId());
        assertEquals("Admin", c.getNombre());
        assertEquals("admin@test.com", c.getCorreo());
        assertEquals("$2a$10$hash", c.getContrasena());
        assertEquals("admin", c.getTipo());
    }

    @Test
    void settersDeberiaSetearCadaCampoIndependientemente() {
        Cuenta c = new Cuenta();

        c.setId(5);
        c.setNombre("Usuario");
        c.setCorreo("user@test.com");
        c.setContrasena("secreto");
        c.setTipo("usuario");

        assertEquals(5, c.getId());
        assertEquals("Usuario", c.getNombre());
        assertEquals("user@test.com", c.getCorreo());
        assertEquals("secreto", c.getContrasena());
        assertEquals("usuario", c.getTipo());
    }

    @Test
    void modificarUnCampoNoDeberiaAfectarOtros() {
        Cuenta c = new Cuenta(1, "Admin", "admin@test.com", "pass", "admin");

        c.setTipo("usuario");

        assertEquals(1, c.getId());
        assertEquals("Admin", c.getNombre());
        assertEquals("admin@test.com", c.getCorreo());
        assertEquals("pass", c.getContrasena());
        assertEquals("usuario", c.getTipo());
    }
}