package com.daledev.mockhousingapp.rest.controller;

import com.daledev.mockhousingapp.rest.dto.CreateInspectionAreaRequestDto;
import com.daledev.mockhousingapp.rest.dto.InspectionAreaDto;
import com.daledev.mockhousingapp.rest.dto.ResultPageDto;
import com.daledev.mockhousingapp.service.InspectionAreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shane.Sturgeon
 * @since 08/03/2018.
 */
@RestController
@RequestMapping("/inspectionArea")
public class InspectionAreaController {
    private static final Logger log = LoggerFactory.getLogger(InspectionAreaController.class);

    private InspectionAreaService inspectionAreaService;

    /**
     * @param inspectionAreaService
     */
    public InspectionAreaController(InspectionAreaService inspectionAreaService) {
        this.inspectionAreaService = inspectionAreaService;
    }

    /**
     * @param requestDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long addAddress(@RequestBody CreateInspectionAreaRequestDto requestDto) {
        log.debug("Request received to add address into inspection area");
        InspectionAreaDto inspectionAreaDto = inspectionAreaService.addInspectionArea(requestDto);
        return inspectionAreaDto.getId();
    }

    /**
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResultPageDto<InspectionAreaDto> getInspectionAreas() {
        log.debug("Request received to get all inspection areaa");
        return inspectionAreaService.getInspectionAreas();
    }
}
