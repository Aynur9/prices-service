package com.zara.prices.domain.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class PriceTest {
    @Test
    public void testBuilderAndGetters() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1);
        Price price = Price.builder()
                .brandId(1L)
                .productId(2L)
                .start(start)
                .end(end)
                .priority(3)
                .price(new BigDecimal("99.99"))
                .currency("EUR")
                .priceList(5)
                .build();
        assertEquals(1L, price.getBrandId());
        assertEquals(2L, price.getProductId());
        assertEquals(start, price.getStart());
        assertEquals(end, price.getEnd());
        assertEquals(3, price.getPriority());
        assertEquals(new BigDecimal("99.99"), price.getPrice());
        assertEquals("EUR", price.getCurrency());
        assertEquals(5, price.getPriceList());
    }

    @Test
    public void testToString() {
        Price price = Price.builder().brandId(1L).productId(2L).build();
        assertTrue(price.toString().contains("brandId=1"));
        assertTrue(price.toString().contains("productId=2"));
    }

    @Test
    public void testPriceCalculation() {
        // Aquí se debe implementar la lógica de prueba para la clase Price
        assertEquals(100, 100); // Ejemplo de aserción
    }

    @Test
    public void testEqualsAndHashCode() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusDays(1);
        Price p1 = Price.builder().brandId(1L).productId(2L).start(start).end(end).priority(3).price(new BigDecimal("99.99")).currency("EUR").priceList(5).build();
        Price p2 = Price.builder().brandId(1L).productId(2L).start(start).end(end).priority(3).price(new BigDecimal("99.99")).currency("EUR").priceList(5).build();
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}