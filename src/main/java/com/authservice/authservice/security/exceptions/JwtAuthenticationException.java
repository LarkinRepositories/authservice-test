package com.authservice.authservice.security.exceptions;

import javax.naming.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String explanation) {
        super(explanation);
    }

    public JwtAuthenticationException() {
    }
}
