package com.scalefocus.springtraining.moviecatalog.model.jwt;

import java.time.LocalDateTime;

/**
 * JwtResponse is required for creating a response containing the JWT
 * to be returned to the user.
 *
 * @author Kristiyan SLavov
 */
public class JwtResponse {

    private final String tokenType;

    private final String token;

    private final LocalDateTime expiresIn;

    public JwtResponse(String tokenType, String token, LocalDateTime expiresIn) {
        this.tokenType = tokenType;
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public LocalDateTime getExpiresIn() {
        return expiresIn;
    }
}
