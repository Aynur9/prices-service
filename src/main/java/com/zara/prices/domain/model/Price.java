package com.zara.prices.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Price {
    private Long brandId;
    private Long productId;
    private LocalDateTime start;
    private LocalDateTime end;
    private Integer priority;
    private BigDecimal price;
    private String currency;
    private Integer priceList;

    // Constructor sin priority
    public Price(Long brandId, Long productId,
                 LocalDateTime start, LocalDateTime end,
                 BigDecimal price, String currency, Integer priceList) {
        this.brandId = brandId;
        this.productId = productId;
        this.start = start;
        this.end = end;
        this.price = price;
        this.currency = currency;
        this.priceList = priceList;
    }
}
