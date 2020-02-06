package com.scalefocus.springtraining.moviecatalog.service.jwt;

import com.scalefocus.springtraining.moviecatalog.util.DateUtils;
import com.scalefocus.springtraining.moviecatalog.util.GeneralConstant;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The JwtTokenService class is responsible for performing JWT operations
 * like creation and validation. It makes use of the {@link io.jsonwebtoken.Jwts}
 * for achieving this.
 *
 * @author Kristiyan SLavov
 */
@Component
public class JwtTokenService {

    private final String secret;

    public JwtTokenService(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
    }

    /**
     * This method retrieves the username from the JWT token
     *
     * @param token - the JWT token
     * @return the username
     */
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * This method retrieves the expiration date from the JWT token
     *
     * @param token - the JWT token
     * @return the expiration date
     */
    public LocalDateTime getExpirationDateFromToken(String token) {
        return DateUtils.asLocalDateTime(getClaimFromToken(token, Claims::getExpiration));
    }

    /**
     * This method gets the claims from the JWT token.
     *
     * @param token          - the JWT token
     * @param claimsResolver - used to resolve the claims
     * @param <T>            - the type of the resolved part (the username type - String,
     *                       or the expirationdate type - localDateTime, etc.)
     * @return the resolved part from the claims
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //check if the token has expired

    /**
     * This method checks if the token is expired.
     *
     * @param token - the JWT token to be checked
     * @return true if the token is expired or false if it is not.
     */
    private Boolean isTokenExpired(String token) {
        final LocalDateTime expiration = getExpirationDateFromToken(token);
        return expiration.isBefore(GeneralConstant.DATE_NOW);
    }

    /**
     * This method validates the given JWT token.
     *
     * @param token       - the JWT token to be validated
     * @param userDetails - the user details
     * @return true if the JWT token is valid or false if it is not.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * This method uses {@link JwtTokenService#doGenerateToken} to generate
     * a JWT token to the specified user.
     *
     * @param userDetails - the specified user
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * This method retrieves any information from the token
     * by using the secret key ("javainuse"). It uses the Jwts.parser method
     * to parse the given JWT token.
     *
     * @param token - thw JWT token to be parsed.
     * @return the claims
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * This method is used to generate a JWT tokens
     *
     * @param claims  - the claims of the token to be defined, like Issued, Expiration, Subject, etc.
     * @param subject - the user for which will be created a new JWT token
     * @return new JWT token
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {
        //while creating the token -
        //1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
        //2. Sign the JWT using the HS512 algorithm and secret key.
        //3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
        //   compaction of the JWT to a URL-safe string

        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(DateUtils.asDate(GeneralConstant.DATE_NOW))
                .setExpiration(DateUtils.asDate(GeneralConstant.DATE_NOW.plusSeconds(3600)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

}
