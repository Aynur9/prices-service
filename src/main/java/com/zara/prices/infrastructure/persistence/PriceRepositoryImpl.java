package com.zara.prices.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
// Limpieza: imports agrupados


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.zara.prices.domain.model.Price;
import com.zara.prices.domain.port.out.PriceRepository;

/**
 * Adaptador de infraestructura para persistencia de precios.
 * <p>Su único rol es traducir las operaciones del dominio a consultas JPA.
 * <p>Se encarga de delegar la consulta a Spring Data y convertir entidades a dominio usando el mapper.
 * <p>No contiene lógica de negocio ni reglas de aplicación.
 */
@Repository
public class PriceRepositoryImpl implements PriceRepository {

    /** Repositorio JPA de Spring Data */
    private final PriceJpaRepository priceJpaRepository;
    private final PriceJpaMapper priceJpaMapper;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param priceJpaRepository repositorio JPA generado por Spring Data
     * @param priceJpaMapper mapper para conversión JPA <-> dominio
     */
    @Autowired
    public PriceRepositoryImpl(PriceJpaRepository priceJpaRepository, PriceJpaMapper priceJpaMapper) {
        this.priceJpaRepository = priceJpaRepository;
        this.priceJpaMapper = priceJpaMapper;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Implementación que delega la consulta a Spring Data JPA y convierte
     * las entidades JPA a objetos de dominio.</p>
     */

    @Override
    public List<Price> findApplicable(Long brandId, Long productId, LocalDateTime date) {
        // Consultar base de datos y convertir entidades JPA a dominio usando el mapper
        return priceJpaRepository.findByBrandIdAndProductIdAndDateBetween(brandId, productId, date)
                .stream()
                .map(priceJpaMapper::toDomain)
                .collect(Collectors.toList());
    }
    // Conversión delegada al mapper

        /**
     * Consulta eficiente para escenarios de alta carga: paginada y proyectada.
     * @param brandId id de marca
     * @param productId id de producto
     * @param date fecha
     * @param page página (0-based)
     * @param size tamaño de página
     * @return página de PriceApplicableDTO
     */
    public Page<PriceApplicableDTO> findApplicableProjected(Long brandId, Long productId, LocalDateTime date, int page, int size) {
        return priceJpaRepository.findApplicableProjected(brandId, productId, date, PageRequest.of(page, size));
    }
}