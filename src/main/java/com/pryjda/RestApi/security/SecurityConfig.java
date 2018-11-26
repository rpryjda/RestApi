package com.pryjda.RestApi.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        User.UserBuilder users = User.withDefaultPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser(users.username("User").password("user123").roles("USER"))
                .withUser(users.username("Admin").password("admin123").roles("USER", "ADMIN"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/students").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/students").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/students/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/students/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/lectures").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/lectures").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/lectures/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/lectures/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/students/*").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/students/*").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/registery/*/*").hasRole("USER")
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
