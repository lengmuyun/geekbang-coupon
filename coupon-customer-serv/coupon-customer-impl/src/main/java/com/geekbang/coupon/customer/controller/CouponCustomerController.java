package com.geekbang.coupon.customer.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.geekbang.coupon.calculation.api.beans.ShoppingCart;
import com.geekbang.coupon.calculation.api.beans.SimulationOrder;
import com.geekbang.coupon.calculation.api.beans.SimulationResponse;
import com.geekbang.coupon.customer.api.beans.RequestCoupon;
import com.geekbang.coupon.customer.api.beans.SearchCoupon;
import com.geekbang.coupon.customer.dao.entity.Coupon;
import com.geekbang.coupon.customer.event.CouponProducer;
import com.geekbang.coupon.customer.service.intf.CouponCustomerService;
import com.geekbang.coupon.template.api.beans.CouponInfo;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("coupon-customer")
public class CouponCustomerController {

    @Autowired
    private CouponCustomerService customerService;

    @Autowired
    private CouponProducer couponProducer;

    @PostMapping("requestCouponEvent")
    public void requestCouponEvent(@Valid @RequestBody RequestCoupon request) {
        couponProducer.sendCoupon(request);
    }

    // 用户删除优惠券
    @DeleteMapping("deleteCouponEvent")
    public void deleteCouponEvent(@RequestParam("userId") Long userId,
                                  @RequestParam("couponId") Long couponId) {
        couponProducer.deleteCoupon(userId, couponId);
    }

    @PostMapping("requestCoupon")
    @SentinelResource(value = "requestCoupon")
    public Coupon requestCoupon(@Valid @RequestBody RequestCoupon request) {
        return customerService.requestCoupon(request);
    }

    // 用户删除优惠券
    @DeleteMapping("deleteCoupon")
    public void deleteCoupon(@RequestParam("userId") Long userId,
                                       @RequestParam("couponId") Long couponId) {
        customerService.deleteCoupon(userId, couponId);
    }

    // 用户模拟计算每个优惠券的优惠价格
    @PostMapping("simulateOrder")
    public SimulationResponse simulate(@Valid @RequestBody SimulationOrder order) {
        return customerService.simulateOrderPrice(order);
    }

    // ResponseEntity - 指定返回状态码 - 可以作为一个课后思考题
    @PostMapping("placeOrder")
    public ShoppingCart checkout(@Valid @RequestBody ShoppingCart info) {
        return customerService.placeOrder(info);
    }


    // 实现的时候最好封装一个search object类
    @PostMapping("findCoupon")
    @SentinelResource(value = "customer-findCoupon")
    public List<CouponInfo> findCoupon(@Valid @RequestBody SearchCoupon request) {
        return customerService.findCoupon(request);
    }

    @DeleteMapping("template")
    @GlobalTransactional(name = "coupon-customer-serv", rollbackFor = Exception.class)
    public void deleteCoupon(@RequestParam("templateId") Long templateId) {
        customerService.deleteCouponTemplate(templateId);
    }

}
