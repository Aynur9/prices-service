package com.zara.prices.application;

import java.time.LocalDateTime;

import com.zara.prices.domain.model.Price;
import com.zara.prices.domain.port.in.GetApplicablePriceUseCase;
import com.zara.prices.domain.port.out.PriceRepository;
import com.zara.prices.domain.service.PriceNotFoundException;

/**
 * Implementación del caso de uso para obtener el precio aplicable.
 * 
 * <p>Esta clase coordina la lógica de aplicación, delegando la selección
 * eficiente del precio al repositorio, que retorna directamente el de mayor prioridad.</p>
 * 
 * <p>Responsabilidades:</p>
 * <ul>
 *   <li>Obtener el precio de mayor prioridad desde el repositorio</li>
 *   <li>Manejar el Optional retornado del repositorio</li>
 *   <li>Lanzar excepción si no hay precio aplicable</li>
 * </ul>
 * 
 * <p>Flujo de ejecución:</p>
 * <ol>
 *   <li>Consulta optimizada al repositorio que trae solo el precio de mayor prioridad</li>
 *   <li>El repositorio retorna Optional con el resultado</li>
 *   <li>Se retorna el precio o se lanza PriceNotFoundException</li>
 * </ol>
 * 
 * <p>Esta implementación es más eficiente que versiones anteriores,
 * resolviendo la selección de tarifa directamente en la extracción de datos.</p>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 2.0
 * @since 2026-02-22
 */
public class GetApplicablePriceService implements GetApplicablePriceUseCase {

    /** Repositorio para acceso a datos de precios */
    private final PriceRepository repository;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param repository implementación del repositorio de precios
     */
    public GetApplicablePriceService(PriceRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Implementación que obtiene directamente el precio de mayor prioridad
     * desde el repositorio sin procesamiento adicional en memoria.</p>
     */
    @Override
    public Price get(Long brandId, Long productId, LocalDateTime date) {
        // Obtener el precio de mayor prioridad directamente desde la BD
        return repository.findHighestPriorityApplicable(brandId, productId, date)
                .orElseThrow(() -> new PriceNotFoundException(
                    "No hay precios aplicables para los parámetros proporcionados"));
    }
}

