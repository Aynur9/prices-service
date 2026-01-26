package com.zara.prices.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

/**
 * Clase de configuración de OpenAPI/Swagger para documentación de la API.
 * 
 * <p>Esta configuración genera automáticamente la documentación interactiva
 * de la API REST, accesible a través de Swagger UI.</p>
 * 
 * <p>URLs de acceso:</p>
 * <ul>
 *   <li>Swagger UI: {@code http://localhost:8080/swagger-ui.html}</li>
 *   <li>OpenAPI JSON: {@code http://localhost:8080/v3/api-docs}</li>
 *   <li>OpenAPI YAML: {@code http://localhost:8080/v3/api-docs.yaml}</li>
 * </ul>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
@Configuration
public class OpenApiConfiguration {

    /**
     * Configura los metadatos de la documentación OpenAPI.
     * 
     * <p>Define información general de la API como título, descripción,
     * versión, contacto, licencia y servidores disponibles.</p>
     * 
     * @return configuración de OpenAPI
     */
    @Bean
    public OpenAPI pricesServiceOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("Prices Service API")
                .description("""
                    API REST para consultar precios aplicables a productos.
                    
                    Esta API permite consultar el precio que debe aplicarse a un producto
                    de una cadena en una fecha específica. Cuando existen múltiples tarifas
                    aplicables, se retorna la de mayor prioridad.
                    
                    ## Características principales:
                    - Consulta de precios por producto, cadena y fecha
                    - Selección automática por prioridad
                    - Arquitectura hexagonal
                    - Base de datos H2 en memoria
                    
                    ## Formato de fechas:
                    Todas las fechas deben estar en formato ISO-8601: `yyyy-MM-dd'T'HH:mm:ss`
                    
                    Ejemplo: `2020-06-14T10:00:00`
                    """)
                .version("1.0.0")
                .contact(new Contact()
                    .name("Zara Technical Team")
                    .email("tech@zara.com")
                    .url("https://www.zara.com"))
                .license(new License()
                    .name("Uso interno")
                    .url("https://www.zara.com/license")))
            .addServersItem(new Server()
                .url("http://localhost:8080")
                .description("Servidor de desarrollo local"))
            .addServersItem(new Server()
                .url("https://api.zara.com")
                .description("Servidor de producción (ejemplo)"));
    }
}
