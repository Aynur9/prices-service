package com.zara.prices.application;

import java.time.LocalDateTime;
import java.util.List;

import com.zara.prices.domain.model.Price;
import com.zara.prices.domain.port.in.GetApplicablePriceUseCase;
import com.zara.prices.domain.port.out.PriceRepository;
import com.zara.prices.domain.service.PriceDomainService;

public class GetApplicablePriceService implements GetApplicablePriceUseCase {

    private final PriceRepository repository;
    private final PriceDomainService domain;

    public GetApplicablePriceService(PriceRepository repository) {
        this.repository = repository;
        this.domain = new PriceDomainService();
    }

    @Override
    public Price get(Long brandId, Long productId, LocalDateTime date) {
        List<Price> prices = repository.findApplicable(brandId, productId, date);
        return domain.selectHighestPriority(prices);
    }
}
