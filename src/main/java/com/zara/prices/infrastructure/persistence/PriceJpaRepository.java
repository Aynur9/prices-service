package com.zara.prices.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repositorio Spring Data JPA para operaciones con la entidad PriceEntity.
 * 
 * <p>Este repositorio proporciona acceso a la base de datos mediante Spring Data JPA,
 * con queries personalizadas optimizadas para buscar precios aplicables.</p>
 * 
 * <p>La consulta principal está optimizada para:</p>
 * <ul>
 *   <li>Filtrar por brandId, productId y rango de fechas en la BD</li>
 *   <li>Ordenar por prioridad descendente para obtener primero el de mayor prioridad</li>
 *   <li>Utilizar índices automáticos en las columnas de búsqueda</li>
 * </ul>
 * 
 *  @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {
    
    /**
     * Busca precios aplicables para un producto de una cadena en una fecha específica.
     * 
     * <p>La query JPQL filtra registros donde:</p>
     * <ul>
     *   <li>brandId coincide con el parámetro</li>
     *   <li>productId coincide con el parámetro</li>
     *   <li>la fecha está entre startDate y endDate (ambos inclusive)</li>
     * </ul>
     * 
     * <p>Los resultados se ordenan por priority DESC, de modo que el primero
     * de la lista es el de mayor prioridad.</p>
     * 
     * @param brandId identificador de la cadena
     * @param productId identificador del producto
     * @param date fecha para verificar aplicabilidad
     * @return lista de precios aplicables ordenados por prioridad descendente
     */
    @Query("SELECT p FROM PriceEntity p WHERE p.brandId = :brandId AND p.productId = :productId " +
           "AND :date BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC")
    List<PriceEntity> findByBrandIdAndProductIdAndDateBetween(
        @Param("brandId") Long brandId,
        @Param("productId") Long productId,
        @Param("date") LocalDateTime date
    );
}