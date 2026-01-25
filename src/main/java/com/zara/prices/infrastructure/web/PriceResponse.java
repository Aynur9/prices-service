package com.zara.prices.infrastructure.web;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.zara.prices.domain.model.Price;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceResponse {
    private Long productId;
    private Long brandId;
    private Integer priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    private String currency;

    public static PriceResponse from(Price price) {
        PriceResponse r = new PriceResponse();
        r.productId = price.getProductId();
        r.brandId = price.getBrandId();
        r.priceList = price.getPriceList();
        r.startDate = price.getStart();
        r.endDate = price.getEnd();
        r.price = price.getPrice();
        r.currency = price.getCurrency();
        return r;
    }
}
