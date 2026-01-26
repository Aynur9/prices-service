package com.zara.prices.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
 * Entidad JPA que representa la tabla PRICES en la base de datos.
 * 
 * <p>Esta clase mapea la estructura de la tabla de base de datos que almacena
 * las tarifas de precios con sus rangos de fechas de aplicación y prioridades.</p>
 * 
 * <p>Estructura de la tabla:</p>
 * <ul>
 *   <li>ID: Clave primaria autogenerada</li>
 *   <li>BRAND_ID: FK a la cadena/marca</li>
 *   <li>PRODUCT_ID: Identificador del producto</li>
 *   <li>PRICE_LIST: Identificador de la tarifa</li>
 *   <li>START_DATE/END_DATE: Rango de vigencia</li>
 *   <li>PRIORITY: Desambiguador (mayor valor = mayor prioridad)</li>
 *   <li>PRICE: Precio final de venta</li>
 *   <li>CURRENCY: Código ISO de moneda</li>
 * </ul>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
@Entity
@Table(name = "PRICES")
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