package com.zara.prices.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Optional;

import com.zara.prices.domain.model.Price;
import com.zara.prices.domain.port.out.PriceRepository;
import com.zara.prices.domain.service.PriceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetApplicablePriceServiceTest {

    private GetApplicablePriceService service;
    private PriceRepository repository;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PriceRepository.class);
        service = new GetApplicablePriceService(repository);
    }

    @Test
    void testGetApplicablePrice_found() {
        // Arrange
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, Month.JUNE, 14, 10, 0);
        Price expectedPrice = new Price(
            brandId,
            productId,
            date.minusHours(1),
            date.plusHours(1),
            1,
            new BigDecimal("35.50"),
            "EUR",
            0
        );
        Mockito.when(repository.findHighestPriorityApplicable(brandId, productId, date))
                .thenReturn(Optional.of(expectedPrice));

        // Act
        Price result = service.get(brandId, productId, date);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("35.50"), result.getPrice());
    }

    @Test
    void testGetApplicablePrice_notFound() {
        // Arrange
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, Month.JUNE, 14, 10, 0);
        Mockito.when(repository.findHighestPriorityApplicable(brandId, productId, date))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(PriceNotFoundException.class, () -> {
            service.get(brandId, productId, date);
        });
    }

    @Test
    void testGetApplicablePrice_highestPrioritySelected() {
        // Arrange
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, Month.JUNE, 14, 16, 0);
        // El repositorio ahora retorna directamente el de mayor prioridad
        Price highestPriority = new Price(
            brandId,
            productId,
            date.minusHours(1),
            date.plusHours(1),
            2,
            new BigDecimal("35.50"),
            "EUR",
            1
        );
        Mockito.when(repository.findHighestPriorityApplicable(brandId, productId, date))
                .thenReturn(Optional.of(highestPriority));

        // Act
        Price result = service.get(brandId, productId, date);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("35.50"), result.getPrice());
        assertEquals(2, result.getPriority());
    }

    @Test
    void testGetApplicablePrice_empty() {
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, Month.JUNE, 14, 10, 0);
        Mockito.when(repository.findHighestPriorityApplicable(brandId, productId, date))
                .thenReturn(Optional.empty());
        assertThrows(PriceNotFoundException.class,
                () -> service.get(brandId, productId, date));
    }

    @Test
    void testGetApplicablePrice_verifyRepositoryOptimization() {
        // Arrange - Este test verifica que se usa el método optimizado
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, Month.JUNE, 14, 10, 0);
        Price price = new Price(
            brandId,
            productId,
            date.minusHours(1),
            date.plusHours(1),
            9999,
            new BigDecimal("35.50"),
            "EUR",
            0
        );
        Mockito.when(repository.findHighestPriorityApplicable(brandId, productId, date))
                .thenReturn(Optional.of(price));

        // Act
        Price result = service.get(brandId, productId, date);

        // Assert - Verifica que se obtuvo el de mayor prioridad desde la BD (sin lógica en memoria)
        assertEquals(9999, result.getPriority());
        assertEquals(new BigDecimal("35.50"), result.getPrice());
        
        // Verifica que se llamó al método optimizado
        Mockito.verify(repository, Mockito.times(1))
                .findHighestPriorityApplicable(brandId, productId, date);
    }
}