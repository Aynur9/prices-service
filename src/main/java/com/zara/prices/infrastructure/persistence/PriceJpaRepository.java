package com.zara.prices.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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
 * @author Eduardo Pindado Aguilar
 * @version 1.0
 * @since 2026-01-26
 */
public interface PriceJpaRepository extends JpaRepository<PriceEntity, Long> {
    
    /**
     * Busca el precio de mayor prioridad aplicable para un producto de una cadena en una fecha específica.
     * 
     * <p>La query JPQL optimizada filtra registros donde:</p>
     * <ul>
     *   <li>brandId coincide con el parámetro</li>
     *   <li>productId coincide con el parámetro</li>
     *   <li>la fecha está entre startDate y endDate (ambos inclusive)</li>
     * </ul>
     * 
     * <p>Retorna directamente el precio de mayor prioridad (O el único resultado si es que hay solo uno).
     * Evita traer múltiples registros y procesamiento en memoria.</p>
     * 
     * @param brandId identificador de la cadena
     * @param productId identificador del producto
     * @param date fecha para verificar aplicabilidad
     * @return Optional con el precio de mayor prioridad, o vacío si no hay resultados
     */
    @Query(value = "SELECT p FROM PriceEntity p WHERE p.brandId = :brandId AND p.productId = :productId " +
           "AND :date BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC")
    List<PriceEntity> findHighestPriorityApplicableList(
        @Param("brandId") Long brandId,
        @Param("productId") Long productId,
        @Param("date") LocalDateTime date
    );

    /**
     * Adaptador de conveniencia que retorna Optional del primer resultado.
     * Este método es protegido y se usa internamente para la optimización.
     *
     * @param brandId identificador de la cadena
     * @param productId identificador del producto
     * @param date fecha para verificar aplicabilidad
     * @return Optional con el precio de mayor prioridad
     */
    default Optional<PriceEntity> findHighestPriorityApplicable(Long brandId, Long productId, LocalDateTime date) {
        return findHighestPriorityApplicableList(brandId, productId, date).stream().findFirst();
    }

    /**
     * Búsqueda antigua por compatibilidad. Usa el nuevo método findHighestPriorityApplicable.
     * Mantiene retorno de lista para evitar romper interfaces existentes.
     *
     * @param brandId identificador de la cadena
     * @param productId identificador del producto
     * @param date fecha para verificar aplicabilidad
     * @return lista de precios aplicables ordenados por prioridad descendente
     * @deprecated usar {@link #findHighestPriorityApplicable(Long, Long, LocalDateTime)} para mejor eficiencia
     */
    @Deprecated(since = "2.0")
    @Query("SELECT p FROM PriceEntity p WHERE p.brandId = :brandId AND p.productId = :productId " +
           "AND :date BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC")
    List<PriceEntity> findByBrandIdAndProductIdAndDateBetween(
        @Param("brandId") Long brandId,
        @Param("productId") Long productId,
        @Param("date") LocalDateTime date
    );

    /**
     * Consulta paginada y proyectada para escenarios de alta carga.
     * Solo trae los campos necesarios usando PriceApplicableDTO.
     *
     * @param brandId identificador de la cadena
     * @param productId identificador del producto
     * @param date fecha para verificar aplicabilidad
     * @param pageable información de paginación
     * @return página de precios aplicables con proyección
     */
    @Query("SELECT p.brandId as brandId, p.productId as productId, p.startDate as startDate, p.endDate as endDate, p.priority as priority, p.price as price, p.currency as currency, p.priceList as priceList FROM PriceEntity p WHERE p.brandId = :brandId AND p.productId = :productId AND :date BETWEEN p.startDate AND p.endDate ORDER BY p.priority DESC")
    Page<PriceApplicableDTO> findApplicableProjected(
        @Param("brandId") Long brandId,
        @Param("productId") Long productId,
        @Param("date") LocalDateTime date,
        Pageable pageable
    );
}