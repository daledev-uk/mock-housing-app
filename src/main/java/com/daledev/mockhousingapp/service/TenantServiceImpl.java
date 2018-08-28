package com.daledev.mockhousingapp.service;

import com.daledev.mockhousingapp.dao.domain.Tenancy;
import com.daledev.mockhousingapp.dao.repository.TenancyDao;
import com.daledev.mockhousingapp.dao.repository.TenantDao;
import com.daledev.mockhousingapp.rest.dto.TenantDto;
import com.daledev.mockhousingapp.util.BeanPropertyUtil;
import com.daledev.mockhousingapp.dao.domain.Tenant;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author dale.ellis
 * @since 15/12/2017
 */
@Service
@Transactional
public class TenantServiceImpl implements TenantService {
    private static final Logger log = LoggerFactory.getLogger(InspectionAreaServiceImpl.class);

    private TenancyDao tenancyDao;
    private TenantDao tenantDao;
    private ModelMapper modelMapper;

    /**
     * @param tenancyDao
     * @param tenantDao
     * @param modelMapper
     */
    public TenantServiceImpl(TenancyDao tenancyDao, TenantDao tenantDao, ModelMapper modelMapper) {
        this.tenancyDao = tenancyDao;
        this.tenantDao = tenantDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public Tenant getTenant(Number tenantId) {
        return tenantId == null ? null : tenantDao.findOne(tenantId.longValue());
    }

    @Override
    public TenantDto updateTenant(TenantDto tenantDto) {
        Tenant tenant = getTenant(tenantDto.getId());

        if (tenant != null) {
            log.debug("Updating existing tenant");
            updateExistingTenant(tenantDto, tenant);
        } else {
            log.debug("Creating new tenant");
            tenant = createNewTenant(tenantDto);
        }

        tenantDao.save(tenant);


        return modelMapper.map(tenant, TenantDto.class);
    }

    @Override
    public void deleteTenant(Long tenantReference) {
        log.debug("Deleting tenant with ID : {}", tenantReference);
        tenantDao.delete(tenantReference);
    }

    @Override
    public void updateTenantField(Long tenantId, String fieldName, Object value) {
        log.debug("Updating tenant(ID : {}) field name '{}' = {}", tenantId, fieldName, value);
        Tenant tenant = tenantDao.findOne(tenantId);

        BeanPropertyUtil.setPropertyValue(tenant, fieldName, value);

        tenant.getTenancy().updateNameAndEmailToLeadTenants();
    }

    @Override
    public String updateTenantEmail(Long tenantId, String email) {
        log.debug("Update tenant(ID : {}) email, setting value to : {}", tenantId, email);
        Tenant tenant = tenantDao.findOne(tenantId);
        String oldEmail = tenant.getEmail();
        tenant.setEmail(email);

        tenant.getTenancy().updateNameAndEmailToLeadTenants();
        return oldEmail;
    }

    private void updateExistingTenant(TenantDto newDetails, Tenant tenant) {
        tenant.setTitle(newDetails.getTitle());
        tenant.setFirstName(newDetails.getFirstName());
        tenant.setLastName(newDetails.getLastName());
        tenant.setEmail(newDetails.getEmail());
        tenant.setContactNumber(newDetails.getContactNumber());

        if (newDetails.isLeadTenant()) {
            tenant.setAsToLeadTenant();
        }

        tenant.getTenancy().updateNameAndEmailToLeadTenants();
    }

    private Tenant createNewTenant(TenantDto newTenant) {
        Tenant tenant;
        tenant = new Tenant();
        tenant.setTitle(newTenant.getTitle());
        tenant.setFirstName(newTenant.getFirstName());
        tenant.setLastName(newTenant.getLastName());
        tenant.setEmail(newTenant.getEmail());
        tenant.setLeadTenant(newTenant.isLeadTenant());

        Tenancy tenancy = tenancyDao.findOne(newTenant.getTenancyReference());
        tenancy.getTenants().add(tenant);
        tenant.setTenancy(tenancy);

        tenancy.updateNameAndEmailToLeadTenants();
        return tenant;
    }

}
