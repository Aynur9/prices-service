package com.zara.prices.domain.service;

import java.util.Comparator;
import java.util.List;

import com.zara.prices.domain.model.Price;

/**
 * Servicio de dominio para selección de precios aplicables.
 * <p>Reglas de negocio puras, sin dependencias externas.
 * <p>Regla principal: Selecciona el precio de mayor prioridad entre múltiples tarifas.
 * <p>Devuelve PriceNotFoundException si no hay precios válidos.
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
        if (prices == null || prices.isEmpty()) {
            throw new PriceNotFoundException("No hay precios aplicables para los parámetros proporcionados");
        }
        return prices.stream()
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException("No hay precios aplicables para los parámetros proporcionados"));
    }
}
