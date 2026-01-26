package com.zara.prices.infrastructure.web;

import org.springframework.stereotype.Component;

import com.zara.prices.domain.model.Price;

/**
 * Mapper para conversión entre entidades de dominio y DTOs de la capa web.
 * 
 * <p>Esta clase se encarga de transformar objetos {@link Price} del dominio
 * a objetos {@link PriceResponse} para la API REST, y viceversa si fuera necesario.</p>
 * 
 * <p>Responsabilidad: Aislar el modelo de dominio de los detalles de la API REST,
 * permitiendo que evolucionen independientemente (desacoplamiento).</p>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
@Component
public class PriceWebMapper {

    /**
     * Convierte una entidad de dominio Price a un DTO de respuesta.
     * 
     * @param price entidad de dominio con la información del precio
     * @return DTO preparado para serialización JSON
     */
    public PriceResponse toResponse(Price price) {
        return new PriceResponse(
            price.getProductId(),
            price.getBrandId(),
            price.getPriceList(),
            price.getStart(),
            price.getEnd(),
            price.getPrice(),
            price.getCurrency()
        );
    }

    public Price toDomain(PriceResponse response) {
        return new Price(
            response.getBrandId(),
            response.getProductId(),
            response.getStartDate(),
            response.getEndDate(),
            response.getPrice(),
            response.getCurrency(),
            response.getPriceList()
        );
    }
}
