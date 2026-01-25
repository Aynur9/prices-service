package com.zara.prices.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.zara.prices.domain.model.Price;
import com.zara.prices.domain.port.out.PriceRepository;

@Repository
public class PriceRepositoryImpl implements PriceRepository {

    private final PriceJpaRepository priceJpaRepository;

    public PriceRepositoryImpl(PriceJpaRepository priceJpaRepository) {
        this.priceJpaRepository = priceJpaRepository;
    }

    @Override
    public List<Price> findApplicable(Long brandId, Long productId, LocalDateTime date) {
        return priceJpaRepository.findByBrandIdAndProductIdAndDateBetween(brandId, productId, date)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Price toDomain(PriceEntity entity) {
        return new Price(
            entity.getBrandId(),
            entity.getProductId(),
            entity.getStartDate(),
            entity.getEndDate(),
            entity.getPriority(),
            entity.getPrice(),
            entity.getCurrency(),
            entity.getPriceList()
        );
    }
}