package com.daledev.mockhousingapp.rest.dto;

import java.io.Serializable;

/**
 * Created by Shane.Sturgeon on 06/03/2018.
 */
public class CreateAddressRequestDto implements Serializable {

    private String line1;
    private String line2;
    private String line3;
    private String town;
    private String postcode;

    /**
     *
     */
    public CreateAddressRequestDto() {
    }

    /**
     * @param addressDto
     */
    public CreateAddressRequestDto(AddressDto addressDto) {
        this.line1 = addressDto.getLine1();
        this.line2 = addressDto.getLine2();
        this.line3 = addressDto.getLine3();
        this.town = addressDto.getTown();
        this.postcode = addressDto.getPostcode();
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine3() {
        return line3;
    }

    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

}
