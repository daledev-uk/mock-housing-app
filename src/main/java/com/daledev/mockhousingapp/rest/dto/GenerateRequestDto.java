package com.daledev.mockhousingapp.rest.dto;

import java.io.Serializable;

/**
 * @author dale.ellis
 * @since 19/12/2017
 */
public class GenerateRequestDto implements Serializable {

    private int total;
    private int tenants;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTenants() {
        return tenants;
    }

    public void setTenants(int tenants) {
        this.tenants = tenants;
    }
}
