package com.scalefocus.springtraining.moviecatalog.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author Kristiyan SLavov
 */
@Configuration
@EnableWebSecurity
public class MovieSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("user1").password("{noop}user1").roles("USER")
                .and()
                .withUser("admin1").password("{noop}admin1").roles("ADMIN");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/movies/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/movies/movie").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/movies/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/movies/**").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin();
    }
}
