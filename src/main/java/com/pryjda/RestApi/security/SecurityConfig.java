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
                .withUser(users.username("jan.nowak@gmail.com").password("1").roles("USER"))
                .withUser(users.username("adam.kowalski@gmail.com").password("2").roles("USER"))
                .withUser(users.username("piotr.rybka@gmail.com").password("3").roles("USER"))
                .withUser(users.username("marcel.chrobry@gmail.com").password("4").roles("USER"))
                .withUser(users.username("robert.mak@gmail.com").password("5").roles("USER"))
                .withUser(users.username("Admin").password("admin123").roles("USER", "ADMIN"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/students").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/students").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/students/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/students/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/students/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/students/alter/password/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/lectures").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/lectures").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/lectures/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/lectures/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/registry/lectures/*").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/my-data").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/my-data").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/alter/password").hasRole("USER")
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
