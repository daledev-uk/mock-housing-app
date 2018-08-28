package com.daledev.mockhousingapp.exception;

import org.springframework.http.HttpStatus;

/**
 * @author dale.ellis
 * @since 26/08/2018
 */
public interface HttpStatusProvider {

    /**
     * Status to be returned when an error is throw
     *
     * @return
     */
    HttpStatus getStatus();
}
