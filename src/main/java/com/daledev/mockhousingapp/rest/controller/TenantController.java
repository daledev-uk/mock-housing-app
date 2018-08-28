package com.daledev.mockhousingapp.rest.controller;

import com.daledev.mockhousingapp.rest.dto.TenantDto;
import com.daledev.mockhousingapp.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author dale.ellis
 * @since 15/12/2017
 */
@RestController
@RequestMapping("/tenant")
public class TenantController {
    private static final Logger log = LoggerFactory.getLogger(TenantController.class);

    private TenantService tenantService;

    /**
     * @param tenantService
     */
    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    /**
     * @param tenantDto
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public TenantDto updateTenant(@RequestBody TenantDto tenantDto) {
        log.debug("Request received to update tenant");
        return tenantService.updateTenant(tenantDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTenant(@PathVariable("id") Long tenantReference) {
        log.debug("Request received to delete tenant");
        tenantService.deleteTenant(tenantReference);
    }
}
