package com.daledev.mockhousingapp.rest.dto;

import java.util.Set;

/**
 * Created by Shane.Sturgeon on 08/03/2018.
 */
public class CreateInspectionAreaRequestDto {

    private String name;
    private Set<Long> addresses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Long> addresses) {
        this.addresses = addresses;
    }
}
