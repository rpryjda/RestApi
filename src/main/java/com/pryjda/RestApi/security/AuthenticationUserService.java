package com.pryjda.RestApi.security;

import com.pryjda.RestApi.repository.UserRepository;
import com.pryjda.RestApi.utils.Helpers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AuthenticationUserService implements UserDetailsService {

    private UserRepository userRepository;

    public AuthenticationUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<com.pryjda.RestApi.entities.User> userByEmail = userRepository
                .findUserByEmail(username);
        Optional<com.pryjda.RestApi.entities.User> userByIndexNumber = Optional.ofNullable(null);

        List<GrantedAuthority> authorities = new ArrayList<>();

        if (Helpers.isNumber(username)) {
            userByIndexNumber = userRepository
                    .findUserByIndexNumber(Integer.parseInt(username));
        }
        if (userByEmail.isPresent()) {
            userByEmail.get().getRoles()
                    .stream()
                    .forEach(item -> authorities.add(new SimpleGrantedAuthority(item.getName())));
            return User.withUsername(userByEmail.get().getEmail())
                    .password(userByEmail.get().getPassword())
                    .authorities(authorities)
                    .build();
        } else if (userByIndexNumber.isPresent()) {
            userByIndexNumber.get().getRoles()
                    .stream()
                    .forEach(item -> authorities.add(new SimpleGrantedAuthority(item.getName())));
            return User.withUsername(userByIndexNumber.get().getIndexNumber().toString())
                    .password(userByIndexNumber.get().getPassword())
                    .authorities(authorities)
                    .build();
        } else {
            throw new UsernameNotFoundException("User wasn't found");
        }
    }
}
