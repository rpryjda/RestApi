package com.pryjda.RestApi;

import com.pryjda.RestApi.entities.Role;
import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.repository.RoleRepository;
import com.pryjda.RestApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        User admin = new User();
        admin.setEmail("Admin");
        admin.setIndexNumber(777);
        admin.setPassword(passwordEncoder.encode("user123"));
        admin.setEnabled(true);
        userRepository.save(admin);

        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");
        Role newRoleNo1 = roleRepository.save(roleAdmin);

        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");
        Role newRoleNo2 = roleRepository.save(roleUser);

        newRoleNo1.getUsers().add(admin);
        newRoleNo2.getUsers().add(admin);
        roleRepository.save(newRoleNo1);
        roleRepository.save(newRoleNo2);
    }
}
