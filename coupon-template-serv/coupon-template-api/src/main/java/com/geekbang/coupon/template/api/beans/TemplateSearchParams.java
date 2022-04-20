package com.geekbang.coupon.template.api.beans;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TemplateSearchParams {

    private Long id;

    private String name;

    private String type;

    private Long shopId;

    private Boolean available;

    private int page;

    private int pageSize;

}
