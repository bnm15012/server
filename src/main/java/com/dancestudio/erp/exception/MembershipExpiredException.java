package com.dancestudio.erp.exception;

public class MembershipExpiredException extends RuntimeException {

    public MembershipExpiredException(String message) {
        super(message);
    }
}
