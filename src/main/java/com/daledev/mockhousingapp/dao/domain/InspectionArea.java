package com.daledev.mockhousingapp.dao.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Shane.Sturgeon on 08/03/2018.
 */
@Entity(name = "inspectionArea")
public class InspectionArea extends AbstractEntity {

    private String name;

    @OneToMany(mappedBy = "inspectionArea", cascade = CascadeType.ALL)
    private Set<Address> addresses = new HashSet<>();

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
