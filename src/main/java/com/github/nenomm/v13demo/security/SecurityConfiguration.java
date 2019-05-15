package com.github.nenomm.v13demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Configures spring security, doing the following:
 * <li>Bypass security checks for static resources,</li>
 * <li>Restrict access to the application, allowing only logged in users,</li>
 * <li>Set up the login form</li>
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${app.urlMapping}")
    private String appUrlMapping;

    private static final String LOGIN_PROCESSING_URL = "login";
    private static final String LOGIN_FAILURE_URL = "login";
    private static final String LOGIN_URL = "login";
    private static final String LOGOUT_SUCCESS_URL = "login";

    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Override
    protected void configure(final AuthenticationManagerBuilder p_auth) throws Exception {
        p_auth.authenticationProvider(authProvider);
    }

    /**
     * Require login to access internal pages and configure login form.
     */
    @Override
    protected void configure(final HttpSecurity p_httpSecurity) throws Exception {

        // Not using Spring CSRF here to be able to use plain HTML for the login page
        p_httpSecurity.csrf().disable()

                // Register our CustomRequestCache, that saves unauthorized access attempts, so
                // the user is redirected after login.
                .requestCache().requestCache(new CustomRequestCache())

                // Restrict access to our application.
                .and().authorizeRequests()

                // Allow all flow internal requests.
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                // Allow all requests by logged in users.
                .anyRequest().authenticated()

                // Configure the login page.
                .and().formLogin().loginPage(appUrlMapping + LOGIN_URL).permitAll().loginProcessingUrl(appUrlMapping + LOGIN_PROCESSING_URL)
                .failureUrl(appUrlMapping + LOGIN_FAILURE_URL)

                // Configure logout
                .and().logout().logoutSuccessUrl(appUrlMapping + LOGOUT_SUCCESS_URL);

        // this is for h2 console - enable this for dev/test profile
        p_httpSecurity.headers().frameOptions().disable();


    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withUsername("user")
                        .password("{noop}pass")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }

    /**
     * Allows access to static resources, bypassing Spring security.
     */
    @Override
    public void configure(final WebSecurity p_web) throws Exception {
        p_web.ignoring().antMatchers(
                // Vaadin Flow static resources
                "/VAADIN/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",

                // icons and images
                "/icons/**",
                "/images/**",

                // (development mode) static resources
                "/frontend/**",

                // (development mode) webjars
                "/webjars/**",

                // (development mode) H2 debugging console
                "/h2-console/**",

                // (production mode) static resources
                "/frontend-es5/**", "/frontend-es6/**");
    }
}
