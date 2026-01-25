package com.zara.prices.infrastructure.persistence;

import org.springframework.stereotype.Component;

import com.zara.prices.domain.model.Price;

@Component
public class PriceJpaMapper {
    public Price toDomain(PriceEntity e) {
        return new Price(
            e.getBrandId(),
            e.getProductId(),
            e.getStartDate(),
            e.getEndDate(),
            e.getPriority(),
            e.getPrice(),
            e.getCurrency(),
            e.getPriceList()
        );
    }
}
