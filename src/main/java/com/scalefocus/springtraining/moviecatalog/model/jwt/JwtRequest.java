package com.scalefocus.springtraining.moviecatalog.model.jwt;

import java.io.Serializable;

/**
 * JwtRequest is required for storing the username and the password
 * we received from the client.
 *
 * @author Kristiyan SLavov
 */
public class JwtRequest implements Serializable {

    private static final long SERIAL_VERSION_UID = 5926468583005150707L;

    private String username;

    private String password;

    public JwtRequest() {
    }

    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
