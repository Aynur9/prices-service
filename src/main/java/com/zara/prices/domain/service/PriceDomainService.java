package com.zara.prices.domain.service;

import java.util.Comparator;
import java.util.List;

import com.zara.prices.domain.model.Price;

/**
 * Servicio de dominio que encapsula la lógica de negocio relacionada con precios.
 * 
 * <p>Este servicio contiene las reglas de negocio puras, sin dependencias de
 * infraestructura. Implementa la lógica de selección de precios aplicables
 * cuando existen múltiples opciones.</p>
 * 
 * <p>Regla principal: Cuando hay varios precios aplicables para un producto
 * en una fecha, se selecciona aquel con el valor más alto en el campo {@code priority}.</p>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
public class PriceDomainService {
    
    /**
     * Selecciona el precio con mayor prioridad de una lista de precios aplicables.
     * 
     * <p>Implementa la regla de desambiguación: cuando múltiples tarifas coinciden
     * en un rango de fechas, se aplica la de mayor prioridad (mayor valor numérico).</p>
     * 
     * @param prices lista de precios aplicables (no debe estar vacía)
     * @return el precio con el valor más alto en el campo priority
     * @throws PriceNotFoundException si la lista está vacía
     */
    public Price selectHighestPriority(List<Price> prices) {
        return prices.stream()
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException());
    }
}
