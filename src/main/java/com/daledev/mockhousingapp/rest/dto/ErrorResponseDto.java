package com.daledev.mockhousingapp.rest.dto;

/**
 * @author dale.ellis
 * @since 26/08/2018
 */
public class ErrorResponseDto {
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
