package com.scalefocus.springtraining.moviecatalog.config;

import com.scalefocus.springtraining.moviecatalog.config.jwt.JwtAuthenticationEntryPoint;
import com.scalefocus.springtraining.moviecatalog.config.jwt.JwtRequestFilter;
import com.scalefocus.springtraining.moviecatalog.service.jwt.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
 * @author Kristiyan SLavov
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MovieSecurityConfig extends WebSecurityConfigurerAdapter {

    //TODO: ADD ROLES AND REFACTOR THE CODE!!!

    //    @Override
    //    public void configure(AuthenticationManagerBuilder auth) throws Exception {
    //
    //        auth.inMemoryAuthentication()
    //                .withUser("user1").password("{noop}user1").roles("USER")
    //                .and()
    //                .withUser("admin1").password("{noop}admin1").roles("ADMIN");
    //    }
    //
    //    @Override
    //    public void configure(HttpSecurity http) throws Exception {
    //        http.httpBasic()
    //                .and()
    //                .authorizeRequests()
    //                .antMatchers(HttpMethod.GET, "/movies/**").hasAnyRole("USER", "ADMIN")
    //                .antMatchers(HttpMethod.POST, "/movies/movie").hasRole("ADMIN")
    //                .antMatchers(HttpMethod.PUT,   "/movies/**").hasRole("ADMIN")
    //                .antMatchers(HttpMethod.DELETE, "/movies/**").hasRole("ADMIN")
    //                .and()
    //                .csrf().disable()
    //                .formLogin();
    //    }


    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    //private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    public MovieSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint /* JwtAccessDeniedHandler jwtAccessDeniedHandler*/ ,
                               JwtUserDetailsService jwtUserDetailsService, JwtRequestFilter jwtRequestFilter) {

        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        //this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    public void configureGlobal(AuthenticationManagerBuilder authManager) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        authManager.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //We don't need CSRF for this example
        httpSecurity.csrf().disable()
                        // don't authenticate this particular request
                        .authorizeRequests().antMatchers("/authenticate").permitAll()
                        //all other requests need to be authenticated
                        .anyRequest().authenticated().and()
                        // make sure we use stateless session;
                        // session won't be used ti store user's state
                        .exceptionHandling()./*accessDeniedHandler(jwtAccessDeniedHandler) */
                        authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .and()
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

















}
