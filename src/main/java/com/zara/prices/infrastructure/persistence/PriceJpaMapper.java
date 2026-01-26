package com.zara.prices.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.zara.prices.domain.model.Price;

/**
 * Mapper para conversión entre entidades JPA y objetos de dominio.
 * 
 * <p>Esta clase se encarga de transformar {@link PriceEntity} (entidades JPA)
 * a objetos {@link Price} del modelo de dominio, y viceversa si fuera necesario.</p>
 * 
 * <p>Propósito: Mantener aislado el modelo de dominio de los detalles de persistencia,
 * permitiendo que evolucionen de forma independiente.</p>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
@Component
public class PriceJpaMapper {
    
    /**
     * Convierte una entidad JPA a un objeto de dominio.
     * 
     * @param e entidad JPA de la base de datos
     * @return objeto de dominio Price
     */
    public Price toDomain(PriceEntity e) {
        return new Price(
            e.getBrandId(),
            e.getProductId(),
            e.getStartDate(),
            e.getEndDate(),
            e.getPriority(),
            e.getPrice(),
            e.getCurrency(),
            e.getPriceList()
        );
    }
}
