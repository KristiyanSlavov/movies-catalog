package com.scalefocus.springtraining.moviecatalog.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * JwtUserDetailsService implements the Spring Security UserDetailsService interface.
 * It overrides the loadUserByUsername for fetching user details from database using the username.
 * The Spring Security Authentication Manager calls this method for getting the user details from
 * the database when authenticating the user details provided by the user. Here we are getting the USER
 * DETAILS FROM HARDCODED USER LIST.Also the password for a user is stored in encrypted format using BCrypt.
 *
 * @author Kristiyan SLavov
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if("javainuse".equals(username)) {
            return new User("javainuse", "$2a$10$qrQUwxcfxGjAaXQnZ66ewe6etIQy8eTi00G4Sxo4mte56oqIzIw8O",
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
