package com.scalefocus.springtraining.moviecatalog.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.springtraining.moviecatalog.model.error.ErrorResponse;
import com.scalefocus.springtraining.moviecatalog.service.jwt.JwtUserDetailsService;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * JwtRequestFilter extends the Spring Web Filter OncePerRequestFilter class.
 * For any incoming request this Filter class get executed.It checks if the
 * request has a valid JWT token.If it has a valid JWT Token then it sets
 * the Authentication in the context, to specify that the current user is
 * authenticated.
 *
 * @author Kristiyan SLavov
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private JwtUserDetailsService jwtUserDetailsService;

    private final ObjectMapper objectMapper;

    private JwtTokenUtil jwtTokenUtil;

    public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService,
                            JwtTokenUtil jwtTokenUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);

        String username = null;
        String jwtToken = null;

        //JWT Token is in the form "Bearer token".
        //Remove Bearer word and get only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            //            jwtToken = requestTokenHeader.substring(7);
            jwtToken = requestTokenHeader.split(" ")[1];
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            } catch (JwtException ex) {
                try (PrintWriter writer = response.getWriter()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType("application/json");
                    writer.write(objectMapper.writeValueAsString(
                            new ErrorResponse("Bad Token",
                                    HttpStatus.UNAUTHORIZED)));
                } catch (IOException e) {
                    logger.warn("An exception occurs in PrintWriter writer");
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                return;
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
            //TODO:REFACTOR
        }

        //Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);

            //If token is valid, configure Spring Security to manually set authentication
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
