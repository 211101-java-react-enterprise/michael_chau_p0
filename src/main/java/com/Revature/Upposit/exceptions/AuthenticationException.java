package com.Revature.Upposit.exceptions;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException() {
        super("Incorrect credentials provided. Could not authenticate.");
    }
}
