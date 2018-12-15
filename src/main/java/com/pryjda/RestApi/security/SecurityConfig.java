package com.pryjda.RestApi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?")
                .authoritiesByUsernameQuery("SELECT username, role FROM user_roles WHERE username = ?");
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
