package com.daledev.mockhousingapp.rest.dto;

import java.util.Set;

/**
 * Created by Shane.Sturgeon on 08/03/2018.
 */
public class InspectionAreaDto {
    private long id;
    private String name;
    private Set<AddressDto> addresses;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AddressDto> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<AddressDto> addresses) {
        this.addresses = addresses;
    }
}
