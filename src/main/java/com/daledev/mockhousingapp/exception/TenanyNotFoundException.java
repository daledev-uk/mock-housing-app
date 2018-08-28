package com.daledev.mockhousingapp.exception;

import org.springframework.http.HttpStatus;

/**
 * @author dale.ellis
 * @since 26/08/2018
 */
public class TenanyNotFoundException extends RuntimeException implements HttpStatusProvider {

    /**
     * @param tenancyReference
     */
    public TenanyNotFoundException(Long tenancyReference) {
        super("No tenancy found with reference : " + tenancyReference);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
