package com.zara.prices.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;

import com.zara.prices.domain.model.Price;
import com.zara.prices.domain.port.out.PriceRepository;
import com.zara.prices.domain.service.PriceDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class GetApplicablePriceServiceTest {

    private GetApplicablePriceService service;
    private PriceRepository repository;

    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PriceRepository.class);
        PriceDomainService domainService = new PriceDomainService();
        service = new GetApplicablePriceService(repository, domainService);
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
        Mockito.when(repository.findApplicable(brandId, productId, date))
                .thenReturn(Collections.singletonList(expectedPrice));

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
        Mockito.when(repository.findApplicable(brandId, productId, date))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(com.zara.prices.domain.service.PriceNotFoundException.class, () -> {
            service.get(brandId, productId, date);
        });
    }

    @Test
    void testGetApplicablePrice_multiplePrices() {
        // Arrange
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, Month.JUNE, 14, 16, 0);
        Price lowPriority = new Price(
            brandId,
            productId,
            date.minusHours(1),
            date.plusHours(1),
            1,
            new BigDecimal("25.45"),
            "EUR",
            0
        );
        Price highPriority = new Price(
            brandId,
            productId,
            date.minusHours(1),
            date.plusHours(1),
            2,
            new BigDecimal("35.50"),
            "EUR",
            1
        );
        Mockito.when(repository.findApplicable(brandId, productId, date))
                .thenReturn(java.util.Arrays.asList(lowPriority, highPriority));

        // Act
        Price result = service.get(brandId, productId, date);

        // Assert
        assertNotNull(result);
        assertEquals(new BigDecimal("35.50"), result.getPrice());
    }

    @Test
    void testGetApplicablePrice_emptyList() {
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, Month.JUNE, 14, 10, 0);
        Mockito.when(repository.findApplicable(brandId, productId, date))
                .thenReturn(Collections.emptyList());
        assertThrows(com.zara.prices.domain.service.PriceNotFoundException.class,
                () -> service.get(brandId, productId, date));
    }

    @Test
    void testGetApplicablePrice_largeList() {
        Long productId = 35455L;
        Long brandId = 1L;
        LocalDateTime date = LocalDateTime.of(2020, Month.JUNE, 14, 10, 0);
        java.util.List<Price> prices = new java.util.ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            prices.add(new Price(brandId, productId, date.minusHours(1), date.plusHours(1), i, new BigDecimal("35.50"), "EUR", 0));
        }
        Mockito.when(repository.findApplicable(brandId, productId, date))
                .thenReturn(prices);
        Price result = service.get(brandId, productId, date);
        assertEquals(9999, result.getPriority());
    }
}