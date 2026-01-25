package com.zara.prices.domain.port.in;

import java.time.LocalDateTime;

import com.zara.prices.domain.model.Price;

public interface GetApplicablePriceUseCase {
    Price get(Long brandId, Long productId, LocalDateTime date);
}
