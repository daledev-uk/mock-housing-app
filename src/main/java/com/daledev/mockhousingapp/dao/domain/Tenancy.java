package com.daledev.mockhousingapp.dao.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dale.ellis
 * @since 14/12/2017
 */
@Entity(name = "tenancy")
public class Tenancy extends AbstractEntity {

    @Column(nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "lead_tenant_email")
    private String email;

    @OneToMany(mappedBy = "tenancy", cascade = CascadeType.ALL)
    private Set<Tenant> tenants = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;

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

    public Set<Tenant> getTenants() {
        return tenants;
    }

    public void setTenants(Set<Tenant> tenants) {
        this.tenants = tenants;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Tenant getLeadTenant() {
        for (Tenant tenant : tenants) {
            if (tenant.isLeadTenant()) {
                return tenant;
            }
        }
        return null;
    }

    public void updateNameAndEmailToLeadTenants() {
        Tenant leadTenant = getLeadTenant();
        setName(leadTenant.getFirstName() + " " + leadTenant.getLastName());
        setEmail(leadTenant.getEmail());
    }
}
