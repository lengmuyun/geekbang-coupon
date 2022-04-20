package com.geekbang.coupon.template.api.beans;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PagedCouponTemplateInfo {

    public List<CouponTemplateInfo> templates;

    public int page;

    public long total;

}
