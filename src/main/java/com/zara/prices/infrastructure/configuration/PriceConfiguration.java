package com.zara.prices.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zara.prices.application.GetApplicablePriceService;
import com.zara.prices.domain.port.in.GetApplicablePriceUseCase;
import com.zara.prices.domain.port.out.PriceRepository;

/**
 * Clase de configuración de Spring para el módulo de precios.
 * 
 * <p>Esta clase configura los beans necesarios para el funcionamiento de la aplicación,
 * especialmente aquellos relacionados con la capa de aplicación que no están
 * anotados directamente con estereotipos de Spring.</p>
 * 
 * <p>Siguiendo arquitectura hexagonal, esta configuración:</p>
 * <ul>
 *   <li>Instancia los casos de uso (capa de aplicación)</li>
 *   <li>Conecta los puertos con sus adaptadores</li>
 *   <li>Gestiona la inyección de dependencias entre capas</li>
 * </ul>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
@Configuration
public class PriceConfiguration {

    /**
     * Configura e instancia el caso de uso para obtener precios aplicables.
     * 
     * <p>Este bean conecta el puerto de entrada (interfaz del caso de uso)
     * con su implementación, inyectando las dependencias necesarias.</p>
     * 
     * @param priceRepository implementación del puerto de salida (inyectada por Spring)
     * @return instancia del caso de uso lista para ser utilizada
     */
    @Bean
    public GetApplicablePriceUseCase getApplicablePriceUseCase(PriceRepository priceRepository) {
        return new GetApplicablePriceService(priceRepository);
    }
}