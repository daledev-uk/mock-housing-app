package com.daledev.mockhousingapp.rest.controller;

import com.daledev.mockhousingapp.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author dale.ellis
 * @since 15/12/2017
 */
@Controller
@RequestMapping("/public")
public class PublicApiController {
    private static final Logger log = LoggerFactory.getLogger(PublicApiController.class);

    private TenantService tenantService;

    /**
     * @param tenantService
     */
    public PublicApiController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    /**
     * @param tenantId
     * @param fieldName
     * @param value
     * @return
     */
    @RequestMapping(value = "update-tenant/{tenantId}/{fieldName}/{value}", method = RequestMethod.POST)
    public void updateTenant(
            @PathVariable("tenantId") Long tenantId,
            @PathVariable("fieldName") String fieldName,
            @PathVariable("value") Object value
    ) {
        log.debug("Request recevied to update tenant field");
        tenantService.updateTenantField(tenantId, fieldName, value);
    }
}
