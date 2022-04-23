package com.geekbang.coupon.customer.service;

import com.geekbang.coupon.template.api.beans.CouponTemplateInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Service
public class WebClientCouponTemplateServiceImpl {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public CouponTemplateInfo loadTemplateInfo(Long couponTemplateId) {
        CouponTemplateInfo templateInfo = webClientBuilder.build()
                .get()
                .uri("http://coupon-template-serv/template/getTemplate?id=" + couponTemplateId)
                .retrieve()
                .bodyToMono(CouponTemplateInfo.class)
                .block();
        return templateInfo;
    }

    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(List<Long> templateIds) {
        Map<Long, CouponTemplateInfo> result = webClientBuilder.build()
                .get()
                .uri("http://coupon-template-serv/template/getBatch?ids=" + StringUtils.join(templateIds, ","))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<Long, CouponTemplateInfo>>(){})
                .block();
        return result;
    }

}
