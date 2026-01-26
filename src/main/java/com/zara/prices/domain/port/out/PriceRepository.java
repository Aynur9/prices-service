package com.zara.prices.domain.port.out;

import java.time.LocalDateTime;
import java.util.List;

import com.zara.prices.domain.model.Price;

/**
 * Puerto de salida para acceso a datos de precios.
 * 
 * <p>Este puerto define el contrato que debe implementar cualquier adaptador
 * de persistencia para acceder a los datos de precios. Forma parte de la capa
 * de dominio en la arquitectura hexagonal.</p>
 * 
 * <p>El puerto abstrae la tecnología de persistencia específica (JPA, MongoDB, etc.),
 * permitiendo cambiar la implementación sin afectar la lógica de negocio.</p>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 * @see com.zara.prices.infrastructure.persistence.PriceRepositoryImpl
 */
public interface PriceRepository {
    
    /**
     * Busca todos los precios aplicables para un producto de una cadena en una fecha.
     * 
     * <p>Retorna una lista con todos los precios cuyo rango de fechas (startDate-endDate)
     * incluye la fecha especificada. Los resultados se ordenan por prioridad descendente.</p>
     * 
     * @param brandId identificador de la cadena
     * @param productId identificador del producto
     * @param date fecha para la cual se buscan precios aplicables
     * @return lista de precios aplicables, ordenados por prioridad descendente
     *         (puede estar vacía si no hay precios aplicables)
     */
    List<Price> findApplicable(Long brandId, Long productId, LocalDateTime date);
}