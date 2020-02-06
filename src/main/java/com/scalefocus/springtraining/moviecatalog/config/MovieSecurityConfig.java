package com.scalefocus.springtraining.moviecatalog.config;

import com.scalefocus.springtraining.moviecatalog.config.jwt.JwtAuthenticationEntryPoint;
import com.scalefocus.springtraining.moviecatalog.config.jwt.JwtRequestFilter;
import com.scalefocus.springtraining.moviecatalog.service.jwt.JwtUserDetailsService;
import com.scalefocus.springtraining.moviecatalog.util.Authority;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * This class allows customization to both WebSecurity and HttpSecurity.
 *
 * @author Kristiyan SLavov
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MovieSecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private JwtUserDetailsService jwtUserDetailsService;

    private JwtRequestFilter jwtRequestFilter;

    public MovieSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                               JwtUserDetailsService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter) {

        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * This method configures AuthenticationManager
     * so that it knows from where to load user for matching credentials.
     * It uses a BCryptPasswordEncode.
     * @param authManager - {@link AuthenticationManagerBuilder} instance that would be configure
     * @throws Exception
     */
    public void configureGlobal(AuthenticationManagerBuilder authManager) throws Exception {
        authManager.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * This method returns a new object of type {@link BCryptPasswordEncoder}.
     * @return - new {@link BCryptPasswordEncoder} instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * This method returns a new object of type {@link AuthenticationManager}.
     * @return - new {@link AuthenticationManager} instance
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * This method is overridden to configure the {@link HttpSecurity}.
     * @param httpSecurity - {@link HttpSecurity} instance to be configured
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //We don't need CSRF for this example
        httpSecurity.csrf().disable()
                // don't authenticate this particular request
                .authorizeRequests()
                .antMatchers("/authenticate").permitAll()
                .antMatchers(HttpMethod.GET, "/movies/**").hasAnyRole(Authority.USER.name(), Authority.ADMIN.name())
                .antMatchers(HttpMethod.POST, "/movies/movie").hasRole(Authority.ADMIN.name())
                .antMatchers(HttpMethod.PUT, "/movies/**").hasRole(Authority.ADMIN.name())
                .antMatchers(HttpMethod.DELETE, "/movies/**").hasRole(Authority.ADMIN.name())
                //all other requests need to be authenticated
                .anyRequest().authenticated().and()
                // make sure we use stateless session;
                // session won't be used ti store user's state
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
