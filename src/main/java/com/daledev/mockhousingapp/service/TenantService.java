package com.daledev.mockhousingapp.service;

import com.daledev.mockhousingapp.rest.dto.TenantDto;
import com.daledev.mockhousingapp.dao.domain.Tenant;

/**
 * @author dale.ellis
 * @since 22/08/2018
 */
public interface TenantService {

    /**
     * @param tenantId
     * @return
     */
    Tenant getTenant(Number tenantId);

    /**
     * @param tenantDto
     * @return
     */
    TenantDto updateTenant(TenantDto tenantDto);

    /**
     * @param tenantReference
     */
    void deleteTenant(Long tenantReference);

    /**
     * @param tenantId
     * @param fieldName
     * @param value
     */
    void updateTenantField(Long tenantId, String fieldName, Object value);

    /**
     * @param tenantId
     * @param email
     * @return
     */
    String updateTenantEmail(Long tenantId, String email);
}
