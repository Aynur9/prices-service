package com.zara.prices.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.zara.prices.domain.model.Price;
import com.zara.prices.domain.port.out.PriceRepository;

/**
 * Implementación JPA del repositorio de precios.
 * 
 * <p>Este adaptador de persistencia implementa el puerto de salida {@link PriceRepository}
 * utilizando Spring Data JPA para acceder a la base de datos H2.</p>
 * 
 * <p>Responsabilidades:</p>
 * <ul>
 *   <li>Ejecutar queries a la base de datos mediante JPA</li>
 *   <li>Convertir entidades JPA a objetos de dominio</li>
 *   <li>Aislar el dominio de los detalles de persistencia</li>
 * </ul>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
@Repository
public class PriceRepositoryImpl implements PriceRepository {

    /** Repositorio JPA de Spring Data */
    private final PriceJpaRepository priceJpaRepository;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param priceJpaRepository repositorio JPA generado por Spring Data
     */
    public PriceRepositoryImpl(PriceJpaRepository priceJpaRepository) {
        this.priceJpaRepository = priceJpaRepository;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Implementación que delega la consulta a Spring Data JPA y convierte
     * las entidades JPA a objetos de dominio.</p>
     */
    @Override
    public List<Price> findApplicable(Long brandId, Long productId, LocalDateTime date) {
        // Consultar base de datos y convertir entidades JPA a dominio
        return priceJpaRepository.findByBrandIdAndProductIdAndDateBetween(brandId, productId, date)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Convierte una entidad JPA a objeto de dominio.
     * 
     * @param entity entidad JPA de la base de datos
     * @return objeto de dominio Price
     */
    private Price toDomain(PriceEntity entity) {
        return new Price(
            entity.getBrandId(),
            entity.getProductId(),
            entity.getStartDate(),
            entity.getEndDate(),
            entity.getPriority(),
            entity.getPrice(),
            entity.getCurrency(),
            entity.getPriceList()
        );
    }
}