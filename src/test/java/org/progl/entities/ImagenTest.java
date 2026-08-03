package org.progl.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ImagenTest {

    @Test
    void constructorVacioDeberiaCrearInstanciaConValoresPorDefecto() {
        Imagen img = new Imagen();

        assertNotNull(img);
        assertEquals(0, img.getId());
        assertNull(img.getFoto());
        assertNull(img.getNombre());
        assertNull(img.getAlt());
    }

    @Test
    void constructorConParametrosDeberiaSetearTodosLosCampos() {
        Imagen img = new Imagen(1, "foto.jpg", "Mi foto", "Descripción alt");

        assertEquals(1, img.getId());
        assertEquals("foto.jpg", img.getFoto());
        assertEquals("Mi foto", img.getNombre());
        assertEquals("Descripción alt", img.getAlt());
    }

    @Test
    void settersDeberiaSetearCadaCampoIndependientemente() {
        Imagen img = new Imagen();

        img.setId(7);
        img.setFoto("imagen.png");
        img.setNombre("Nombre test");
        img.setAlt("Alt test");

        assertEquals(7, img.getId());
        assertEquals("imagen.png", img.getFoto());
        assertEquals("Nombre test", img.getNombre());
        assertEquals("Alt test", img.getAlt());
    }

    @Test
    void modificarUnCampoNoDeberiaAfectarOtros() {
        Imagen img = new Imagen(1, "foto.jpg", "Nombre", "Alt");

        img.setId(99);

        assertEquals(99, img.getId());
        assertEquals("foto.jpg", img.getFoto());
        assertEquals("Nombre", img.getNombre());
        assertEquals("Alt", img.getAlt());
    }
}