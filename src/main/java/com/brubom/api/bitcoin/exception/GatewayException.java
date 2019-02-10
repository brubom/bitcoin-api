package com.brubom.api.bitcoin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class GatewayException extends RuntimeException {

    public GatewayException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }

}
