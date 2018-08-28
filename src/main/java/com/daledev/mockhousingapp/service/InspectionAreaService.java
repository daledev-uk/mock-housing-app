package com.daledev.mockhousingapp.service;

import com.daledev.mockhousingapp.rest.dto.CreateInspectionAreaRequestDto;
import com.daledev.mockhousingapp.rest.dto.InspectionAreaDto;
import com.daledev.mockhousingapp.rest.dto.ResultPageDto;

/**
 * @author dale.ellis
 * @since 22/08/2018
 */
public interface InspectionAreaService {
    /**
     *
     * @param request
     * @return
     */
    InspectionAreaDto addInspectionArea(CreateInspectionAreaRequestDto request);

    /**
     *
     * @return
     */
    ResultPageDto<InspectionAreaDto> getInspectionAreas();
}
