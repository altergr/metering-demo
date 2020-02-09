package com.github.altergr.meteringdemo.domain.exception;

public class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 439150355475606182L;

    public EntityNotFoundException(String message) {
        super(message);
    }
}
