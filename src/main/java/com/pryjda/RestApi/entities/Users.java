package com.pryjda.RestApi.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    private String username;
    private String password;
    private boolean enabled;

    @OneToMany(mappedBy = "user")
    private Set<UserRoles> roles = new HashSet<>();
}
