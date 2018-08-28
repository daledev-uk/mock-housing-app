package com.daledev.mockhousingapp.service;

import com.daledev.mockhousingapp.dao.domain.Tenancy;
import com.daledev.mockhousingapp.rest.dto.ResultPageDto;
import com.daledev.mockhousingapp.rest.dto.TenancyDto;

/**
 * @author dale.ellis
 * @since 22/08/2018
 */
public interface TenancyAccessService {
    /**
     * @param tenancyReference
     * @return
     */
    Tenancy getTenancy(long tenancyReference);

    /**
     * @param itemsPerPage
     * @param pageNumber
     * @param sortDir
     * @param sort
     * @param searchParam
     * @return
     */
    ResultPageDto<TenancyDto> findTenancies(int itemsPerPage, int pageNumber, String sortDir, String sort, String searchParam);
}
