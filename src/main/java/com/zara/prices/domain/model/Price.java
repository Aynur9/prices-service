package com.zara.prices.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Entidad de dominio que representa el precio de un producto.
 * 
 * <p>Esta clase encapsula la información completa de una tarifa de precio,
 * incluyendo el rango de fechas de aplicación, la prioridad para desambiguación
 * y el precio final.</p>
 * 
 * <p>Cuando múltiples tarifas son aplicables para un producto en una fecha,
 * se selecciona aquella con mayor valor de {@code priority}.</p>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Price {
    /** Identificador de la cadena/marca (ej: 1 = ZARA) */
    private Long brandId;
    /** Identificador del producto */
    private Long productId;
    /** Fecha y hora de inicio de aplicación del precio */
    private LocalDateTime start;
    /** Fecha y hora de fin de aplicación del precio */
    private LocalDateTime end;
    /** Prioridad para desambiguación. Mayor valor = mayor prioridad */
    private Integer priority;
    /** Precio final de venta (PVP) */
    private BigDecimal price;
    /** Código ISO de la moneda (ej: EUR, USD) */
    private String currency;
    /** Identificador de la tarifa de precios */
    private Integer priceList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price priceObj = (Price) o;
        return java.util.Objects.equals(brandId, priceObj.brandId)
                && java.util.Objects.equals(productId, priceObj.productId)
                && java.util.Objects.equals(start, priceObj.start)
                && java.util.Objects.equals(end, priceObj.end)
                && java.util.Objects.equals(priority, priceObj.priority)
                && java.util.Objects.equals(price, priceObj.price)
                && java.util.Objects.equals(currency, priceObj.currency)
                && java.util.Objects.equals(priceList, priceObj.priceList);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(brandId, productId, start, end, priority, price, currency, priceList);
    }

    /**
     * Constructor alternativo sin el campo priority.
     * Útil cuando la prioridad no es relevante o se asignará posteriormente.
     * 
     * @param brandId identificador de la cadena
     * @param productId identificador del producto
     * @param start fecha de inicio
     * @param end fecha de fin
     * @param price precio final
     * @param currency código de moneda
     * @param priceList identificador de tarifa
     */
    public Price(Long brandId, Long productId,
                 LocalDateTime start, LocalDateTime end,
                 BigDecimal price, String currency, Integer priceList) {
        this.brandId = brandId;
        this.productId = productId;
        this.start = start;
        this.end = end;
        this.price = price;
        this.currency = currency;
        this.priceList = priceList;
    }
}
