package com.zara.prices.domain.port.in;

import java.time.LocalDateTime;

import com.zara.prices.domain.model.Price;

/**
 * Puerto de entrada (caso de uso) para obtener el precio aplicable.
 * 
 * <p>Este puerto define el contrato para consultar el precio que debe aplicarse
 * a un producto de una cadena en una fecha específica. Es la interfaz que expone
 * la lógica de negocio al mundo exterior.</p>
 * 
 * <p>Siguiendo arquitectura hexagonal, este puerto pertenece a la capa de dominio
 * y será implementado por la capa de aplicación.</p>
 * 
 * <p>Reglas de negocio:</p>
 * <ul>
 *   <li>Se selecciona el precio cuyo rango de fechas incluye la fecha consultada</li>
 *   <li>Si hay múltiples precios aplicables, se retorna el de mayor prioridad</li>
 *   <li>Si no existe precio aplicable, se lanza {@code PriceNotFoundException}</li>
 * </ul>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 * @see com.zara.prices.domain.model.Price
 * @see com.zara.prices.domain.service.PriceNotFoundException
 */
public interface GetApplicablePriceUseCase {
    
    /**
     * Obtiene el precio aplicable para un producto de una cadena en una fecha específica.
     * 
     * @param brandId identificador de la cadena (ej: 1 = ZARA)
     * @param productId identificador del producto
     * @param date fecha y hora para la cual se consulta el precio
     * @return el precio aplicable con mayor prioridad
     * @throws com.zara.prices.domain.service.PriceNotFoundException si no existe precio aplicable
     */
    Price get(Long brandId, Long productId, LocalDateTime date);
}
