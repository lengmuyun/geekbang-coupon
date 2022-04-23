package com.geekbang.coupon.customer.service;

import com.geekbang.coupon.calculation.api.beans.ShoppingCart;
import com.geekbang.coupon.calculation.api.beans.SimulationOrder;
import com.geekbang.coupon.calculation.api.beans.SimulationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClientCouponCalculationServiceImpl {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public SimulationResponse simulateOrder(SimulationOrder order) {
        SimulationResponse result = webClientBuilder.build()
                .post()
                .uri("http://coupon-calculation-serv/calculator/simulate")
                .bodyValue(order)
                .retrieve()
                .bodyToMono(SimulationResponse.class)
                .block();
        return result;
    }

    public ShoppingCart calculateOrderPrice(ShoppingCart order) {
        ShoppingCart result = webClientBuilder.build()
                .post()
                .uri("http://coupon-calculation-serv/calculator/checkout")
                .bodyValue(order)
                .retrieve()
                .bodyToMono(ShoppingCart.class)
                .block();
        return result;
    }

}
