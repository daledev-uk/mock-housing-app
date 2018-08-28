package com.daledev.mockhousingapp.rest.dto;

import java.io.Serializable;

/**
 * @author dale.ellis
 * @since 15/12/2017
 */
public class TenantDto implements Serializable {
    private Long id;
    private String title;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private boolean leadTenant;

    private Long tenancyReference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public boolean isLeadTenant() {
        return leadTenant;
    }

    public void setLeadTenant(boolean leadTenant) {
        this.leadTenant = leadTenant;
    }

    public Long getTenancyReference() {
        return tenancyReference;
    }

    public void setTenancyReference(Long tenancyReference) {
        this.tenancyReference = tenancyReference;
    }
}
