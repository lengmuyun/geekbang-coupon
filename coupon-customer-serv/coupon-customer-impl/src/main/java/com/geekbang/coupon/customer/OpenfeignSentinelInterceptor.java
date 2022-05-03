package com.geekbang.coupon.customer;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenfeignSentinelInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header("SentinelSource", "coupon-customer-serv");
    }

}
