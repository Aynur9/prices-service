package com.zara.prices.application;

import java.time.LocalDateTime;
import java.util.List;

import com.zara.prices.domain.model.Price;
import com.zara.prices.domain.port.in.GetApplicablePriceUseCase;
import com.zara.prices.domain.port.out.PriceRepository;
import com.zara.prices.domain.service.PriceDomainService;

/**
 * Implementación del caso de uso para obtener el precio aplicable.
 * 
 * <p>Esta clase coordina la lógica de aplicación, usando las llamadas
 * al repositorio y al servicio de dominio para ejecutar el caso de uso.</p>
 * 
 * <p>Responsabilidades:</p>
 * <ul>
 *   <li>Obtener precios aplicables desde el repositorio</li>
 *   <li>Delegar la selección del precio correcto al servicio de dominio</li>
 *   <li>Retornar el precio final o propagar excepciones</li>
 * </ul>
 * 
 * <p>Flujo de ejecución:</p>
 * <ol>
 *   <li>Consulta al repositorio por precios aplicables</li>
 *   <li>El repositorio retorna lista ordenada por prioridad</li>
 *   <li>El servicio de dominio selecciona el de mayor prioridad</li>
 *   <li>Se retorna el precio o se lanza PriceNotFoundException</li>
 * </ol>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
public class GetApplicablePriceService implements GetApplicablePriceUseCase {

    /** Repositorio para acceso a datos de precios */
    private final PriceRepository repository;
    
    /** Servicio de dominio con lógica de negocio */
    private final PriceDomainService domain;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param repository implementación del repositorio de precios
     */
    public GetApplicablePriceService(PriceRepository repository) {
        this.repository = repository;
        this.domain = new PriceDomainService();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Implementación que combina consulta al repositorio y aplicación
     * de reglas de negocio para determinar el precio final.</p>
     */
    @Override
    public Price get(Long brandId, Long productId, LocalDateTime date) {
        // Obtener todos los precios aplicables (ordenados por prioridad)
        List<Price> prices = repository.findApplicable(brandId, productId, date);
        
        // Seleccionar el de mayor prioridad
        return domain.selectHighestPriority(prices);
    }
}
