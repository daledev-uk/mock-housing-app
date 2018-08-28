package com.daledev.mockhousingapp.service;

import com.daledev.mockhousingapp.dao.domain.Address;
import com.daledev.mockhousingapp.rest.dto.AddressDto;
import com.daledev.mockhousingapp.rest.dto.CreateAddressRequestDto;
import com.daledev.mockhousingapp.rest.dto.ResultPageDto;

/**
 * @author dale.ellis
 * @since 22/08/2018
 */
public interface AddressService {
    /**
     *
     * @param addressId
     * @return
     */
    Address getAddress(Number addressId);

    /**
     *
     * @param addressId
     * @return
     */
    boolean addressExists(Number addressId);

    /**
     * @return
     */
    ResultPageDto<AddressDto> getAllAddresses();

    /**
     * @param request
     * @return
     */
    AddressDto addAddress(CreateAddressRequestDto request);

    /**
     * @param id
     */
    void deleteAddress(long id);

    /**
     * @param postCode
     */
    void addAddressFromPostcode(String postCode);


}
