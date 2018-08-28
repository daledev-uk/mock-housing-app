package com.daledev.mockhousingapp.rest.controller;

import com.daledev.mockhousingapp.rest.dto.ErrorResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.daledev.mockhousingapp.exception.HttpStatusProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author dale.ellis
 * @since 26/08/2018
 */
@ControllerAdvice
public class ErrorResponseHandler {
    private static final Logger log = LoggerFactory.getLogger(ErrorResponseHandler.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * This provides exception handling for any exception thrown up to a controller.
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> ruleExceptionHandler(Exception ex) {
        log.error("Exception caught by @ExceptionHandler(Exception.class) has received exception : {}", ex.getClass().getName());

        ErrorResponseDto response = new ErrorResponseDto();
        response.setErrorMessage(ex.getMessage());

        if (ex instanceof HttpStatusProvider) {
            HttpStatusProvider httpStatusProvider = (HttpStatusProvider) ex;
            return createResponse(response, httpStatusProvider.getStatus());
        } else {
            return createResponse(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<String> createResponse(ErrorResponseDto response, HttpStatus httpStatus) {
        String jsonResponse;
        try {
            jsonResponse = OBJECT_MAPPER.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            jsonResponse = "Unexpected error, failed to parse response";
            log.error("Could not convert response object into JSON", e);
        }
        return new ResponseEntity<>(jsonResponse, httpStatus);
    }

}
