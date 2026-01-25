package com.zara.prices.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zara.prices.application.GetApplicablePriceService;
import com.zara.prices.domain.port.in.GetApplicablePriceUseCase;
import com.zara.prices.domain.port.out.PriceRepository;

@Configuration
public class PriceConfiguration {

    @Bean
    public GetApplicablePriceUseCase getApplicablePriceUseCase(PriceRepository priceRepository) {
        return new GetApplicablePriceService(priceRepository);
    }
}