package com.zara.prices.domain.service;

/**
 * Excepción de dominio lanzada cuando no se encuentra un precio aplicable.
 * 
 * <p>Esta excepción se lanza cuando se consulta por un precio que no existe
 * o cuando no hay ningún precio aplicable para los parámetros proporcionados
 * (brandId, productId, date).</p>
 * 
 * <p>Al ser una {@code RuntimeException}, no requiere declaración explícita
 * en las firmas de métodos, manteniendo las interfaces limpias.</p>
 * 
 * <p>En la capa de presentación (REST), esta excepción se traduce a un
 * código HTTP 404 Not Found.</p>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
public class PriceNotFoundException extends RuntimeException {

    /**
     * Constructor por defecto con mensaje estándar.
     */
    public PriceNotFoundException() {
        super("Price not found");
    }

    /**
     * Constructor con mensaje personalizado.
     * 
     * @param message descripción del error
     */
    public PriceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa raíz.
     * 
     * @param message descripción del error
     * @param cause excepción que causó este error
     */
    public PriceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}