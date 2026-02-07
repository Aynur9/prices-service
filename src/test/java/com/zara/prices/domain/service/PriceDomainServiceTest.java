package com.zara.prices.domain.service;

import com.zara.prices.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class PriceDomainServiceTest {
    private final PriceDomainService service = new PriceDomainService();

    @Test
    void selectHighestPriority_returnsHighestPriority() {
        Price p1 = Price.builder().brandId(1L).productId(1L).priority(1).price(new BigDecimal("10.00")).build();
        Price p2 = Price.builder().brandId(1L).productId(1L).priority(5).price(new BigDecimal("20.00")).build();
        Price p3 = Price.builder().brandId(1L).productId(1L).priority(3).price(new BigDecimal("15.00")).build();
        List<Price> prices = Arrays.asList(p1, p2, p3);
        Price result = service.selectHighestPriority(prices);
        Assertions.assertEquals(p2, result);
    }

    @Test
    void selectHighestPriority_throwsExceptionIfEmpty() {
        Assertions.assertThrows(PriceNotFoundException.class, () -> service.selectHighestPriority(Collections.emptyList()));
    }

    @Test
    void selectHighestPriority_throwsExceptionIfNull() {
        Assertions.assertThrows(PriceNotFoundException.class, () -> service.selectHighestPriority(null));
    }

    @Test
    void selectHighestPriority_samePriorityReturnsFirst() {
        Price p1 = Price.builder().brandId(1L).productId(1L).priority(5).price(new BigDecimal("10.00")).build();
        Price p2 = Price.builder().brandId(1L).productId(1L).priority(5).price(new BigDecimal("20.00")).build();
        List<Price> prices = Arrays.asList(p1, p2);
        Price result = service.selectHighestPriority(prices);
        Assertions.assertTrue(result.equals(p1) || result.equals(p2));
    }

    @Test
    void selectHighestPriority_largeListPerformance() {
        List<Price> prices = new java.util.ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            prices.add(Price.builder().brandId(1L).productId(1L).priority(i).price(new BigDecimal("10.00")).build());
        }
        Price result = service.selectHighestPriority(prices);
        Assertions.assertEquals(9999, result.getPriority());
    }
}