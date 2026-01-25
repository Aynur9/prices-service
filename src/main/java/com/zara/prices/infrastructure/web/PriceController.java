package com.zara.prices.infrastructure.web;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zara.prices.domain.model.Price;
import com.zara.prices.domain.port.in.GetApplicablePriceUseCase;

@RestController
@RequestMapping("/prices")
public class PriceController {

    private final GetApplicablePriceUseCase useCase;
    private final PriceWebMapper mapper;

    public PriceController(GetApplicablePriceUseCase useCase, PriceWebMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping
    public PriceResponse get(
        @RequestParam Long brandId,
        @RequestParam Long productId,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {

        Price price = useCase.get(brandId, productId, date);

        return mapper.toResponse(price);
    }
}
