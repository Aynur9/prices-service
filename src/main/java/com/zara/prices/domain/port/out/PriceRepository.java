package com.zara.prices.domain.port.out;

import java.time.LocalDateTime;
import java.util.List;

import com.zara.prices.domain.model.Price;

public interface PriceRepository {
    List<Price> findApplicable(Long brandId, Long productId, LocalDateTime date);
}