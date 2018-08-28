package com.daledev.mockhousingapp.service;

import com.daledev.mockhousingapp.rest.dto.AddAddressToTenancyRequestDto;
import com.daledev.mockhousingapp.rest.dto.CreateTenancyRequestDto;
import com.daledev.mockhousingapp.rest.dto.GenerateRequestDto;
import com.daledev.mockhousingapp.rest.dto.TenancyDto;

/**
 * @author dale.ellis
 * @since 22/08/2018
 */
public interface TenancyService {
    /**
     * @param tenancyDto
     * @return
     */
    TenancyDto createTenancy(CreateTenancyRequestDto tenancyDto);

    /**
     * @param tenancyReference
     */
    void deleteTenancy(Long tenancyReference);

    /**
     * @param request
     */
    void generateTenancies(GenerateRequestDto request);

    /**
     * @param request
     */
    void setAddressForTenancy(AddAddressToTenancyRequestDto request);
}
