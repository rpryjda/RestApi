package com.pryjda.RestApi;

import com.pryjda.RestApi.entities.Role;
import com.pryjda.RestApi.entities.User;
import com.pryjda.RestApi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {


        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");

        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");

        User admin = new User();
        admin.setEmail("Admin");
        admin.setIndexNumber(777);
        admin.setPassword(passwordEncoder.encode("user123"));
        admin.setEnabled(true);
        admin.getRoles().add(roleAdmin);
        admin.getRoles().add(roleUser);

        userRepository.save(admin);
    }
}
