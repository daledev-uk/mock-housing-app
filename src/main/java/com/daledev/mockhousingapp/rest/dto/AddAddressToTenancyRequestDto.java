package com.daledev.mockhousingapp.rest.dto;

/**
 * Created by Shane.Sturgeon on 08/03/2018.
 */
public class AddAddressToTenancyRequestDto {

    private long tenancyId;
    private long addressId;

    public long getTenancyId() {
        return tenancyId;
    }

    public void setTenancyId(long tenancyId) {
        this.tenancyId = tenancyId;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }
}
