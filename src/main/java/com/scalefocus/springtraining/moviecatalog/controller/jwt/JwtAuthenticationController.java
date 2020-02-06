package com.scalefocus.springtraining.moviecatalog.controller.jwt;

import com.scalefocus.springtraining.moviecatalog.model.jwt.JwtRequest;
import com.scalefocus.springtraining.moviecatalog.model.jwt.JwtResponse;
import com.scalefocus.springtraining.moviecatalog.service.jwt.JwtTokenService;
import com.scalefocus.springtraining.moviecatalog.util.ErrorMessage;
import com.scalefocus.springtraining.moviecatalog.util.GeneralConstant;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Expose a POST API/authenticate using the JwtAuthenticationController.
 * The POST API gets username and password in the body- Using Spring Authentication
 * Manager we authenticate the username and password. If the credentials are valid,
 * a JWT token is created using the JwtTokenUtil and provided it to the client.
 *
 * @author Kristiyan SLavov
 */
@RestController
@CrossOrigin
public class JwtAuthenticationController {

    private AuthenticationManager authenticationManager;

    private JwtTokenService jwtTokenService;

    private UserDetailsService jwtUserDetailsService;

    public JwtAuthenticationController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, UserDetailsService jwtUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    /**
     * This method uses {@link JwtAuthenticationController#authenticate} method
     * to authenticate the {@link JwtRequest}which contains
     * the username and the password of the user. If the authentication is
     * successful then this method returns a {@link ResponseEntity} with
     * {@link JwtResponse} instance which contains a token, token type and token expiry date.
     *
     * @param authenticationRequest - the {@link JwtRequest} authenticationRequest
     * @return {@link ResponseEntity} with {@link JwtResponse}
     */
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenService.generateToken(userDetails);
        final LocalDateTime tokenExpirationDate = jwtTokenService.getExpirationDateFromToken(token);

        return ResponseEntity.ok(new JwtResponse(GeneralConstant.BEARER_TOKEN_TYPE, token, tokenExpirationDate));
    }

    /**
     * This method gets the username and the password and uses {@link AuthenticationManager#authenticate}
     * to authenticate the username and the password of the user
     *
     * @param username - the username
     * @param password - the password
     */
    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new DisabledException(ErrorMessage.USER_DISABLED.toString(), e);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(ErrorMessage.INVALID_CREDENTIALS.toString(), e);
        }
    }
}
