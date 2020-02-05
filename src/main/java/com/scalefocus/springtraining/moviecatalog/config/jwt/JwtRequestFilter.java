package com.scalefocus.springtraining.moviecatalog.config.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalefocus.springtraining.moviecatalog.model.error.ErrorResponse;
import com.scalefocus.springtraining.moviecatalog.service.jwt.JwtTokenService;
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
 * For any incoming request this JwtRequestFilter class get executed.
 * First it checks if the request has a JWT token, and if it has
 * this class validates it using {@link JwtTokenService} for it.
 * Otherwise JwtRequestFilter class references to {@link com.scalefocus.springtraining.moviecatalog.controller.jwt.JwtAuthenticationController}
 * for checking user's authentication.
 *
 *
 * @author Kristiyan SLavov
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BAD_TOKEN_MSG = "Bad Token";

    private static final String APP_JSON_CONTENT_TYPE = "application/json";

    private static final String PRINT_WRITER_EXCEPTION_MSG = "An exception occurs in PrintWriter writer";

    private JwtUserDetailsService jwtUserDetailsService;

    private final ObjectMapper objectMapper;

    private JwtTokenService jwtTokenService;

    public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService,
                            JwtTokenService jwtTokenService) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenService = jwtTokenService;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * This method checks if the request has a JWT token and if it has,
     * this method uses {@link JwtTokenService} to validate the JWT token.
     * If the request has a valid JWT Token then it sets the Authentication in the context,
     * to specify that the current user is authenticated
     * Otherwise, JwtRequestFilter class references
     * to {@link com.scalefocus.springtraining.moviecatalog.controller.jwt.JwtAuthenticationController}
     * for checking user's authentication and generating token.
     *
     * @param request - HttpServletRequest request used for getting the headers
     * @param response - HttpServletResponse response used for sending feedback to the user
     * @param chain - Causes the next filter in the chain to be invoked, or if the calling
     * filter is the last filter in the chain, causes the resource at the end of
     * the chain to be invoked.
     * @throws ServletException if the processing fails for any other reason
     * @throws IOException if an I/O error occurs during the processing of the request
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
    throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        String username = null;
        String jwtToken = null;

        //JWT Token is in the form "Bearer token".
        //Remove Bearer word and get only the Token
        //TODO: handle the different cases with request token header: " bearer", " bearer ", " Bearer ", etc.
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.split(" ")[1];
            try {
                username = jwtTokenService.getUsernameFromToken(jwtToken);
            } catch (JwtException ex) {
                try (PrintWriter writer = response.getWriter()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.setContentType(APP_JSON_CONTENT_TYPE);
                    writer.write(objectMapper.writeValueAsString(
                            new ErrorResponse(BAD_TOKEN_MSG,
                                    HttpStatus.UNAUTHORIZED)));
                } catch (IOException e) {
                    logger.warn(PRINT_WRITER_EXCEPTION_MSG);
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
            if (jwtTokenService.validateToken(jwtToken, userDetails)) {
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
