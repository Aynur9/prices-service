package com.zara;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot para el servicio de precios.
 * 
 * <p>Esta aplicación implementa un servicio REST que permite consultar el precio
 * aplicable a un producto de una cadena en una fecha determinada, siguiendo
 * una arquitectura hexagonal (puertos y adaptadores).</p>
 * 
 * <p>Características principales:</p>
 * <ul>
 *   <li>API REST para consulta de precios</li>
 *   <li>Base de datos H2 en memoria</li>
 *   <li>Arquitectura hexagonal con separación de capas</li>
 *   <li>Aplicación de principios SOLID</li>
 * </ul>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
@SpringBootApplication
public class DemoApplication {

    /**
     * Método principal que arranca la aplicación Spring Boot.
     * 
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}