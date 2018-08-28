package com.daledev.mockhousingapp.rest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author dale.ellis
 * @since 15/12/2017
 */
public class TenancyDto implements Serializable {
    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private String email;
    private Set<TenantDto> tenants;
    private AddressDto address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<TenantDto> getTenants() {
        return tenants;
    }

    public void setTenants(Set<TenantDto> tenants) {
        this.tenants = tenants;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }
}
