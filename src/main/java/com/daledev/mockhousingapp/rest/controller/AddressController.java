package com.daledev.mockhousingapp.rest.controller;

import com.daledev.mockhousingapp.rest.dto.CreateAddressRequestDto;
import com.daledev.mockhousingapp.rest.dto.ResultPageDto;
import com.daledev.mockhousingapp.rest.dto.AddressDto;
import com.daledev.mockhousingapp.service.AddressService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Shane.Sturgeon
 * @since 06/03/2018.
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    private static final Logger log = LoggerFactory.getLogger(AddressController.class);

    private AddressService addressService;

    /**
     * @param addressService
     */
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    /**
     * @return
     */
    @RequestMapping(value = "/getAddresses", method = RequestMethod.GET)
    public ResultPageDto<AddressDto> getAllAddresses() {
        log.debug("Request received to get all addresses");
        return addressService.getAllAddresses();
    }

    /**
     * @param requestDto
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Long addAddress(@RequestBody CreateAddressRequestDto requestDto) {
        log.debug("Request received to add address");
        return addressService.addAddress(requestDto).getId();
    }

    /**
     * @param addressId
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteAddress(@PathVariable("id") Long addressId) {
        log.debug("Request received to delete address with ID : {}", addressId);
        addressService.deleteAddress(addressId);
    }

    /**
     * @param postCode
     */
    @RequestMapping(value = "/add/postcode/{postCode}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addAddressFromPostcode(@PathVariable("postCode") String postCode) {
        log.debug("Request received to add all addresses for post code : {}", postCode);
        addressService.addAddressFromPostcode(postCode);
    }

}
