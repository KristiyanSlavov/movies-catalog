package com.scalefocus.springtraining.moviecatalog.config.jwt;

import io.jsonwebtoken.JwtException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JwtAuthenticationEntryPoint will implements Spring's AuthenticationEntryPoint
 * interface and override its method commence. It rejects every unauthenticated request
 * and send error code 401.
 *
 * @author Kristiyan SLavov
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final String UNAUTHORIZED_MSG = "Unauthorized";

    /**
     * {@inheritDoc}
     **/
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, JwtException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, UNAUTHORIZED_MSG);
    }
}
