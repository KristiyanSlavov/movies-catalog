package com.scalefocus.springtraining.moviecatalog.model.jwt;

import java.io.Serializable;

/**
 * JwtResponse is required for creating a response containing the JWT
 * to be returned to the user.
 *
 * @author Kristiyan SLavov
 */
public class JwtResponse implements Serializable {

    private static final long SERIAL_VERSION_UID = -8091879091924046844L;

    private final String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
