package com.daledev.mockhousingapp.service;

import com.daledev.mockhousingapp.rest.dto.AddressDto;

import java.util.List;

/**
 * @author dale.ellis
 * @since 22/08/2018
 */
public interface AddressLookupService {

    /**
     * Returns all addresses matching the given postcode
     *
     * @param postCode
     * @return
     */
    List<AddressDto> findAddressByPostCode(String postCode);

}
