package com.zara.prices.infrastructure.web;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zara.prices.domain.model.Price;
import com.zara.prices.domain.port.in.GetApplicablePriceUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador REST para operaciones relacionadas con precios.
 * 
 * <p>Este controlador expone endpoints HTTP para consultar precios aplicables.
 * Actúa como adaptador REST en la arquitectura hexagonal, traduciendo
 * peticiones HTTP a llamadas al caso de uso de dominio.</p>
 * 
 * <p>Responsabilidades:</p>
 * <ul>
 *   <li>Validar y parsear parámetros HTTP</li>
 *   <li>Delegar lógica de negocio al caso de uso</li>
 *   <li>Convertir entidades de dominio a DTOs de respuesta</li>
 *   <li>Manejar excepciones y retornar códigos HTTP apropiados</li>
 * </ul>
 * 
 * <p>Endpoint base: {@code /prices}</p>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
@RestController
@RequestMapping("/prices")
@Tag(name = "Precios", description = "API de consulta de precios aplicables a productos")
public class PriceController {

    /** Caso de uso para obtener precio aplicable */
    private final GetApplicablePriceUseCase useCase;
    
    /** Mapper para conversión entre modelos de dominio y DTOs */
    private final PriceWebMapper mapper;

    /**
     * Constructor con inyección de dependencias.
     * 
     * @param useCase caso de uso de consulta de precios
     * @param mapper convertidor de entidades
     */
    public PriceController(GetApplicablePriceUseCase useCase, PriceWebMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    /**
     * Endpoint GET para consultar el precio aplicable a un producto.
     * 
     * <p>Retorna el precio que debe aplicarse para un producto de una cadena
     * en una fecha específica. Si existen múltiples tarifas aplicables,
     * se retorna la de mayor prioridad.</p>
     * 
     * <p>Ejemplo de uso:</p>
     * <pre>
     * GET /prices?date=2020-06-14T10:00:00&productId=35455&brandId=1
     * </pre>
     * 
     * <p>Respuesta exitosa (200 OK):</p>
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
     * @param brandId identificador de la cadena (ej: 1 = ZARA)
     * @param productId identificador del producto
     * @param date fecha y hora en formato ISO-8601 (ej: 2020-06-14T10:00:00)
     * @return DTO con la información completa del precio aplicable
     * @throws com.zara.prices.domain.service.PriceNotFoundException si no hay precio aplicable (retorna 404)
     */
    @Operation(
        summary = "Consultar precio aplicable",
        description = "Obtiene el precio aplicable para un producto de una cadena en una fecha específica. " +
                      "Si existen múltiples tarifas aplicables, retorna la de mayor prioridad.",
        tags = { "Precios" }
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Precio encontrado exitosamente",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = PriceResponse.class),
                examples = @ExampleObject(
                    name = "Ejemplo de respuesta",
                    value = """
                        {
                          "productId": 35455,
                          "brandId": 1,
                          "priceList": 1,
                          "startDate": "2020-06-14T00:00:00",
                          "endDate": "2020-12-31T23:59:59",
                          "price": 35.50,
                          "currency": "EUR"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404",
            description = "No se encontró precio aplicable para los parámetros proporcionados",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    name = "Error - Precio no encontrado",
                    value = """
                        {
                          "timestamp": "2026-01-26T20:00:00",
                          "status": 404,
                          "error": "Not Found",
                          "message": "Price not found",
                          "path": "/prices"
                        }
                        """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Parámetros de entrada inválidos",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                examples = @ExampleObject(
                    name = "Error - Parámetros inválidos",
                    value = """
                        {
                          "timestamp": "2026-01-26T20:00:00",
                          "status": 400,
                          "error": "Bad Request",
                          "message": "Invalid date format",
                          "path": "/prices"
                        }
                        """
                )
            )
        )
    })
    @GetMapping
    public PriceResponse get(
        @Parameter(
            name = "brandId",
            description = "Identificador de la cadena/marca (ej: 1 = ZARA)",
            required = true,
            example = "1",
            schema = @Schema(type = "integer", format = "int64")
        )
        @RequestParam Long brandId,
        
        @Parameter(
            name = "productId",
            description = "Identificador del producto",
            required = true,
            example = "35455",
            schema = @Schema(type = "integer", format = "int64")
        )
        @RequestParam Long productId,
        
        @Parameter(
            name = "date",
            description = "Fecha y hora de consulta en formato ISO-8601",
            required = true,
            example = "2020-06-14T10:00:00",
            schema = @Schema(type = "string", format = "date-time", pattern = "yyyy-MM-dd'T'HH:mm:ss")
        )
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        // Ejecutar caso de uso
        Price price = useCase.get(brandId, productId, date);

        // Convertir entidad de dominio a DTO de respuesta
        return mapper.toResponse(price);
    }
}
