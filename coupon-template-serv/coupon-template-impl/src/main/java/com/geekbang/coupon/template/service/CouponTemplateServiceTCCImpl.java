package com.geekbang.coupon.template.service;

import com.geekbang.coupon.template.api.beans.CouponTemplateInfo;
import com.geekbang.coupon.template.api.beans.PagedCouponTemplateInfo;
import com.geekbang.coupon.template.api.beans.TemplateSearchParams;
import com.geekbang.coupon.template.dao.CouponTemplateDao;
import com.geekbang.coupon.template.dao.entity.CouponTemplate;
import com.geekbang.coupon.template.service.intf.CouponTemplateServiceTCC;
import io.seata.rm.tcc.api.BusinessActionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CouponTemplateServiceTCCImpl implements CouponTemplateServiceTCC {

    @Autowired
    private CouponTemplateDao templateDao;

    @Override
    public CouponTemplateInfo createTemplate(CouponTemplateInfo request) {
        return null;
    }

    @Override
    public CouponTemplateInfo cloneTemplate(Long templateId) {
        return null;
    }

    @Override
    public PagedCouponTemplateInfo search(TemplateSearchParams request) {
        return null;
    }

    @Override
    public CouponTemplateInfo loadTemplateInfo(Long id) {
        return null;
    }

    @Override
    public void deleteTemplate(Long id) {

    }

    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {
        return null;
    }

    @Override
    public void deleteTemplateTCC(Long id) {
        CouponTemplate filter = CouponTemplate.builder()
                .available(true)
                .locked(false)
                .id(id)
                .build();

        CouponTemplate template = templateDao.findAll(Example.of(filter))
                .stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Template Not Found"));

        template.setLocked(true);
        templateDao.save(template);
    }

    @Override
    @Transactional
    public void deleteTemplateCommit(BusinessActionContext context) {
        Long id = Long.parseLong(context.getActionContext("id").toString());
        CouponTemplate couponTemplate = templateDao.findById(id).get();

        couponTemplate.setLocked(false);
        couponTemplate.setAvailable(false);
        templateDao.save(couponTemplate);

        log.info("TCC committed");
    }

    @Override
    @Transactional
    public void deleteTemplateCancel(BusinessActionContext context) {
        Long id = Long.parseLong(context.getActionContext("id").toString());
        Optional<CouponTemplate> templateOptional = templateDao.findById(id);

        if (templateOptional.isPresent()) {
            CouponTemplate template = templateOptional.get();
            template.setLocked(false);
            templateDao.save(template);
        }
        log.info("TCC cancel");
    }

}
