package com.zara.prices.infrastructure.web;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.zara.prices.domain.model.Price;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO (Data Transfer Object) para la respuesta del endpoint de consulta de precios.
 * 
 * <p>Esta clase encapsula los datos que se retornan al cliente en formato JSON
 * cuando se consulta un precio. Actúa como contrato de la API REST.</p>
 * 
 * <p>Ejemplo de JSON generado:</p>
 * <pre>
 * {
 *   "productId": 35455,
 *   "brandId": 1,
 *   "priceList": 1,
 *   "startDate": "2020-06-14T00:00:00",
 *   "endDate": "2020-12-31T23:59:59",
 *   "price": 35.50,
 *   "currency": "EUR"
 * }
 * </pre>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
@Schema(description = "Respuesta con información del precio aplicable")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceResponse {
    
    /** Identificador del producto */
    @Schema(
        description = "Identificador del producto",
        example = "35455",
        required = true
    )
    private Long productId;
    
    /** Identificador de la cadena/marca */
    @Schema(
        description = "Identificador de la cadena/marca (1 = ZARA)",
        example = "1",
        required = true
    )
    private Long brandId;
    
    /** Identificador de la tarifa aplicable */
    @Schema(
        description = "Identificador de la tarifa de precios aplicable",
        example = "1",
        required = true
    )
    private Integer priceList;
    
    /** Fecha de inicio de aplicación del precio */
    @Schema(
        description = "Fecha de inicio de aplicación del precio",
        example = "2020-06-14T00:00:00",
        type = "string",
        format = "date-time",
        required = true
    )
    private LocalDateTime startDate;
    
    /** Fecha de fin de aplicación del precio */
    @Schema(
        description = "Fecha de fin de aplicación del precio",
        example = "2020-12-31T23:59:59",
        type = "string",
        format = "date-time",
        required = true
    )
    private LocalDateTime endDate;
    
    /** Precio final de venta */
    @Schema(
        description = "Precio final de venta (PVP)",
        example = "35.50",
        type = "number",
        format = "decimal",
        required = true
    )
    private BigDecimal price;
    
    /** Código ISO de la moneda */
    @Schema(
        description = "Código ISO de la moneda",
        example = "EUR",
        required = true
    )
    private String currency;

    /**
     * Factory method para crear un PriceResponse desde una entidad de dominio Price.
     * 
     * @param price entidad de dominio
     * @return DTO con los datos mapeados
     * @deprecated Usar {@link PriceWebMapper#toResponse(Price)} en su lugar
     */
    @Deprecated
    public static PriceResponse from(Price price) {
        PriceResponse r = new PriceResponse();
        r.productId = price.getProductId();
        r.brandId = price.getBrandId();
        r.priceList = price.getPriceList();
        r.startDate = price.getStart();
        r.endDate = price.getEnd();
        r.price = price.getPrice();
        r.currency = price.getCurrency();
        return r;
    }
}
