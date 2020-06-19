package com.scalefocus.springtraining.moviecatalog.service.jwt;

import com.scalefocus.springtraining.moviecatalog.util.ErrorMessage;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;

/**
 * JwtUserDetailsService implements the Spring Security UserDetailsService interface.
 * It overrides the loadUserByUsername for fetching user details from database using the username.
 * The Spring Security Authentication Manager calls this method for getting the user details from
 * the database when authenticating the user details provided by the user. Here we are getting the USER
 * DETAILS FROM HARDCODED USER LIST. Also the password for a user is stored in encrypted format using BCrypt.
 *
 * @author Kristiyan SLavov
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserDetails javaInUseUser;

    private final UserDetails testUser;

    public JwtUserDetailsService() {
        //BCrypted pass: 123456789
        javaInUseUser = new User("javainuse",
                "$2a$12$xVqzk8Fq6rZRfuXX106o8.hvDJMoPQYPeGzbt7ahOahvM6lOE0xlG",
                Arrays.asList(() -> "ROLE_ADMIN", () -> "ROLE_USER"));

        //BCrypted pass: 12345678
        testUser = new User("testuser",
                "$2a$12$39OFhWEhoEjiieD8CT9tXe7XFwsk9R6taifWrRek6O18OvXMHjVeC",
                Collections.singletonList(() -> "ROLE_USER"));
    }

    /**
     * This method is used to load a user by the specified username.
     * Actually this method works only with two hardcoded users {@link JwtUserDetailsService#javaInUseUser},
     * {@link JwtUserDetailsService#testUser} and if the specified name
     * is equal to one of these users usernames it will return one of these {@link UserDetails} objects.
     *
     * @param username - the specified username
     * @return a new {@link UserDetails} instance
     *
     * @throws UsernameNotFoundException if the username is not equal to one of the given objects.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (javaInUseUser.getUsername().equals(username)) {
            return javaInUseUser;
        } else if (testUser.getUsername().equals(username)) {
            return testUser;
        } else {
            throw new UsernameNotFoundException(ErrorMessage.USER_NOT_FOUND.toString());
        }
    }
}
