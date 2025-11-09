package com.proyecto.cabapro.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class NewsArticleTest {

    @Test
    public void testGetters() {
      
        NewsArticle article = new NewsArticle(
            "Título de prueba",
            "Descripción de prueba",
            "http://linkdeprueba.com",
            "http://imagen.com/imagen.jpg",
            "Fuente de prueba"
        );

        // Verificación de los gett and sett del modelo
        assertEquals("Título de prueba", article.getTitle());
        assertEquals("Descripción de prueba", article.getDescription());
        assertEquals("http://linkdeprueba.com", article.getLink());
        assertEquals("http://imagen.com/imagen.jpg", article.getImageUrl());
        assertEquals("Fuente de prueba", article.getSource());
    }
}
