package com.daledev.mockhousingapp.exception;

import org.springframework.http.HttpStatus;

/**
 * @author dale.ellis
 * @since 26/08/2018
 */
public class AddressNotFoundException extends RuntimeException implements HttpStatusProvider {

    /**
     * @param addressId
     */
    public AddressNotFoundException(Long addressId) {
        super("No address found with ID : " + addressId);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
