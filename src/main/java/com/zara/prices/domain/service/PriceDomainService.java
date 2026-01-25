package com.zara.prices.domain.service;

import java.util.Comparator;
import java.util.List;

import com.zara.prices.domain.model.Price;

public class PriceDomainService {
    public Price selectHighestPriority(List<Price> prices) {
        return prices.stream()
                .max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException());
    }
}
