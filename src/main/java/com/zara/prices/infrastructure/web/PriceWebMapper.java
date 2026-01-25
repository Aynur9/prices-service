package com.zara.prices.infrastructure.web;

import org.springframework.stereotype.Component;

import com.zara.prices.domain.model.Price;

@Component
public class PriceWebMapper {

    public PriceResponse toResponse(Price price) {
        return new PriceResponse(
            price.getProductId(),
            price.getBrandId(),
            price.getPriceList(),
            price.getStart(),
            price.getEnd(),
            price.getPrice(),
            price.getCurrency()
        );
    }

    public Price toDomain(PriceResponse response) {
        return new Price(
            response.getBrandId(),
            response.getProductId(),
            response.getStartDate(),
            response.getEndDate(),
            response.getPrice(),
            response.getCurrency(),
            response.getPriceList()
        );
    }
}
