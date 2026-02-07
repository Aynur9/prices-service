package com.zara.prices.infrastructure.persistence;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface PriceApplicableDTO {
    Long getBrandId();
    Long getProductId();
    LocalDateTime getStartDate();
    LocalDateTime getEndDate();
    Integer getPriority();
    BigDecimal getPrice();
    String getCurrency();
    Integer getPriceList();
}
