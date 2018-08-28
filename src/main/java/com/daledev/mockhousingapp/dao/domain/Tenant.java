package com.daledev.mockhousingapp.dao.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * @author dale.ellis
 * @since 14/12/2017
 */
@Entity(name = "tenant")
public class Tenant extends AbstractEntity {

    @Column(length = 20)
    private String title;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column
    private String email;

    @Column(name = "contact_number")
    private String contactNumber;

    @Column(name = "is_lead_tenant")
    private boolean leadTenant;

    @ManyToOne
    private Tenancy tenancy;

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

    public Tenancy getTenancy() {
        return tenancy;
    }

    public void setTenancy(Tenancy tenancy) {
        this.tenancy = tenancy;
    }

    /**
     * Unmark the current lead tenant and make this tenant the load tenant
     */
    public void setAsToLeadTenant() {
        if (getTenancy().getLeadTenant() != null) {
            getTenancy().getLeadTenant().setLeadTenant(false);
        }
        setLeadTenant(true);
    }
}
