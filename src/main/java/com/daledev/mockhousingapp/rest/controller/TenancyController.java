package com.daledev.mockhousingapp.rest.controller;

import com.daledev.mockhousingapp.rest.dto.*;
import com.daledev.mockhousingapp.service.TenancyAccessService;
import com.daledev.mockhousingapp.service.TenancyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author dale.ellis
 * @since 15/12/2017
 */
@RestController
@RequestMapping("/tenancy")
public class TenancyController {
    private static final Logger log = LoggerFactory.getLogger(TenancyController.class);

    private TenancyAccessService tenancyAccessService;
    private TenancyService tenancyService;

    /**
     * @param tenancyAccessService
     * @param tenancyService
     */
    public TenancyController(TenancyAccessService tenancyAccessService, TenancyService tenancyService) {
        this.tenancyAccessService = tenancyAccessService;
        this.tenancyService = tenancyService;
    }

    /**
     * @param itemsPerPage
     * @param pageIndex    0 based page index
     * @param sortDir
     * @param sort
     * @return
     */
    @RequestMapping(value = "/{itemsPerPage}/{pageNumber}", method = RequestMethod.GET)
    public ResultPageDto<TenancyDto> findTenancies(
            @PathVariable("itemsPerPage") int itemsPerPage,
            @PathVariable("pageNumber") int pageIndex,
            @RequestParam(required = false) String sortDir,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String searchParam
    ) {
        log.debug("Request received to find tenancies");
        return tenancyAccessService.findTenancies(itemsPerPage, pageIndex, sortDir, sort, searchParam);
    }

    /**
     * @param requestDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long addTenancy(@RequestBody CreateTenancyRequestDto requestDto) {
        log.debug("Request received to add tenancy");
        TenancyDto tenancyDto = tenancyService.createTenancy(requestDto);
        return tenancyDto.getId();
    }

    /**
     * @param request
     * @return
     */
    @RequestMapping(value = "/generate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Integer generateTenancies(@RequestBody GenerateRequestDto request) {
        log.debug("Request received to generate tenancies");
        tenancyService.generateTenancies(request);
        return request.getTotal();
    }

    /**
     * @param tenancyReference
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTenant(@PathVariable("id") Long tenancyReference) {
        log.debug("Request received to delete tenancy {}", tenancyReference);
        tenancyService.deleteTenancy(tenancyReference);
    }

    /**
     * @param request
     */
    @RequestMapping(value = "/addAddress", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addAddress(@RequestBody AddAddressToTenancyRequestDto request) {
        log.debug("Request received to associate address with tenancy");
        tenancyService.setAddressForTenancy(request);
    }


}
