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
                .usersByUsernameQuery("SELECT email, CONCAT('{noop}', password), enabled FROM user WHERE email = ?")
                .authoritiesByUsernameQuery("SELECT u.email, r.name FROM role AS r, user AS u, user_role AS ur " +
                        "WHERE u.id = ur.user_id AND r.id = ur.role_id AND u.email = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/users").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/users/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/users/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/users/password/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/lectures").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/lectures").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/lectures/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/lectures/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/registry/lectures/*").hasRole("USER")
                .antMatchers(HttpMethod.GET, "/my-data").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/my-data").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/password").hasRole("USER")
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
