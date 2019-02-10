package com.brubom.api.bitcoin.exception;

public class RepositoryException extends RuntimeException {

    public RepositoryException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
