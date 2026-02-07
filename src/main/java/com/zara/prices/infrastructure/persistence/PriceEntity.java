package com.zara.prices.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;
// Limpieza: imports agrupados

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Entidad de infraestructura para persistencia.
 * <p>Su único rol es mapear la tabla PRICES a un objeto Java.
 * <p>No contiene lógica de negocio ni reglas de aplicación.
 */
@Entity
@Table(
    name = "PRICES",
    indexes = {
        @jakarta.persistence.Index(name = "idx_brand_product", columnList = "BRAND_ID,PRODUCT_ID"),
        @jakarta.persistence.Index(name = "idx_start_date", columnList = "START_DATE"),
        @jakarta.persistence.Index(name = "idx_end_date", columnList = "END_DATE")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class PriceEntity {
    /** Clave primaria autogenerada */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Identificador del producto */
    @ToString.Include
    @Column(name = "PRODUCT_ID", nullable = false)
    private Long productId;

    /** Identificador de la cadena/marca (1 = ZARA) */
    @ToString.Include
    @Column(name = "BRAND_ID", nullable = false)
    private Long brandId;

    /** Identificador de la tarifa de precios */
    @Column(name = "PRICE_LIST")
    private Integer priceList;

    /** Fecha de inicio de aplicación del precio */
    @Column(name = "START_DATE")
    private LocalDateTime startDate;

    /** Fecha de fin de aplicación del precio */
    @Column(name = "END_DATE")
    private LocalDateTime endDate;

    /** Prioridad para desambiguación (mayor valor = mayor prioridad) */
    @Column(name = "PRIORITY")
    private Integer priority;

    /** Precio final de venta (PVP) */
    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "CURRENCY")
    private String currency;
}