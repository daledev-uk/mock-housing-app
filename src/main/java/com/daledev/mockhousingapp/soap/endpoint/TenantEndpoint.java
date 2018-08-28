package com.daledev.mockhousingapp.soap.endpoint;

import com.daledev.mockhousingapp.dao.domain.Tenancy;
import com.daledev.mockhousingapp.rest.dto.TenantDto;
import com.daledev.mockhousingapp.dao.domain.Tenant;
import com.daledev.mockhousingapp.service.TenancyAccessService;
import com.daledev.mockhousingapp.service.TenantService;
import com.mock_housing_app.soap_services.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.transaction.Transactional;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author dale.ellis
 * @since 02/01/2018
 */
@Endpoint
@Transactional
public class TenantEndpoint {
    private static final String NAMESPACE_URI = "http://mock-housing-app.com/soap-services";

    private TenantService tenantService;
    private TenancyAccessService tenancyAccessService;

    /**
     * @param tenantService
     * @param tenancyAccessService
     */
    public TenantEndpoint(TenantService tenantService, TenancyAccessService tenancyAccessService) {
        this.tenantService = tenantService;
        this.tenancyAccessService = tenancyAccessService;
    }

    /**
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateTenantEmailRequest")
    @ResponsePayload
    public UpdateTenantEmailResponse updateEmail(@RequestPayload UpdateTenantEmailRequest request) {
        UpdateTenantEmailResponse response = new UpdateTenantEmailResponse();

        response.setPersonReference(request.getPersonReference());
        response.setOldEmail(tenantService.updateTenantEmail(request.getPersonReference(), request.getEmail()));
        response.setNewEmail(request.getEmail());

        return response;
    }

    /**
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateTenantRequest")
    @ResponsePayload
    public UpdateTenantResponse updateTenantRequest(@RequestPayload UpdateTenantRequest request) {
        Tenant tenant = tenantService.getTenant(request.getPersonReference());
        TenantDto tenantUpdates = new TenantDto();
        tenantUpdates.setId(request.getPersonReference());
        tenantUpdates.setTitle(request.getTitle() == null || request.getTitle().isEmpty() ? tenant.getTitle() : request.getTitle());
        tenantUpdates.setFirstName(request.getFirstName() == null || request.getFirstName().isEmpty() ? tenant.getFirstName() : request.getFirstName());
        tenantUpdates.setLastName(request.getLastName() == null || request.getLastName().isEmpty() ? tenant.getLastName() : request.getLastName());
        tenantUpdates.setEmail(request.getEmail() == null || request.getEmail().isEmpty() ? tenant.getEmail() : request.getEmail());
        tenantUpdates.setContactNumber(request.getContactNumber() == null || request.getContactNumber().isEmpty() ? tenant.getContactNumber() : request.getContactNumber());
        tenantUpdates.setLeadTenant(request.isLeadTenant() == null ? tenant.isLeadTenant() : request.isLeadTenant());
        tenantUpdates.setTenancyReference(tenant.getTenancy().getId());

        TenantDto tenantDto = tenantService.updateTenant(tenantUpdates);

        UpdateTenantResponse response = new UpdateTenantResponse();
        response.setTenant(convertTenantToWsTenant(tenant));
        return response;
    }

    /**
     * @param request
     * @return
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getTenancyRequest")
    @ResponsePayload
    public GetTenancyResponse getTenancy(@RequestPayload GetTenancyRequest request) {
        Tenancy tenancy = tenancyAccessService.getTenancy(request.getTenancyReference());

        GetTenancyResponse response = new GetTenancyResponse();
        response.setTenancyReference(request.getTenancyReference());
        response.setName(tenancy.getName());
        response.setEmail(tenancy.getEmail());
        response.setStartDate(toXMLDate(tenancy.getStartDate()));
        response.setEndDate(toXMLDate(tenancy.getEndDate()));

        for (Tenant tenant : tenancy.getTenants()) {
            response.getTenants().add(convertTenantToWsTenant(tenant));
        }

        return response;
    }

    private com.mock_housing_app.soap_services.Tenant convertTenantToWsTenant(Tenant tenant) {
        com.mock_housing_app.soap_services.Tenant wsTenant = new com.mock_housing_app.soap_services.Tenant();
        wsTenant.setPersonReference(tenant.getId());
        wsTenant.setTitle(tenant.getTitle());
        wsTenant.setFirstName(tenant.getFirstName());
        wsTenant.setLastName(tenant.getLastName());
        wsTenant.setEmail(tenant.getEmail());
        wsTenant.setContactNumber(tenant.getContactNumber());
        wsTenant.setLeadTenant(tenant.isLeadTenant());
        return wsTenant;
    }

    private XMLGregorianCalendar toXMLDate(Date date) {
        if (date == null) {
            return null;
        }
        try {
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(date);
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException e) {
            return null;
        }
    }

}
