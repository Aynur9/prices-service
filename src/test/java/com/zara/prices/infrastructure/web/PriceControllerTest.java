package com.zara.prices.infrastructure.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    static void setup() {
        System.setProperty("net.bytebuddy.experimental", "true");
    }

    @Test
    void test1_priceAt20200614_10() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("date", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.priceList").value(1));
    }

    @Test
    void test2_priceAt20200614_16() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("date", "2020-06-14T16:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.priceList").value(2));
    }

    @Test
    void test3_priceAt20200614_21() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("date", "2020-06-14T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.priceList").value(1));
    }

    @Test
    void test4_priceAt20200615_10() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("date", "2020-06-15T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(30.50))
                .andExpect(jsonPath("$.priceList").value(3));
    }

    @Test
    void test5_priceAt20200616_21() throws Exception {
        mockMvc.perform(get("/prices")
                        .param("date", "2020-06-16T21:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.priceList").value(4));
    }
}