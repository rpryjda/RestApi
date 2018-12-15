package com.pryjda.RestApi;

import com.pryjda.RestApi.entities.UserRoles;
import com.pryjda.RestApi.entities.Users;
import com.pryjda.RestApi.repository.UserRolesRepository;
import com.pryjda.RestApi.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Users admin = new Users();
        admin.setUsername("Admin");
        admin.setPassword("{noop}admin123");
        admin.setEnabled(true);
        usersRepository.save(admin);

        UserRoles adminRole = new UserRoles();
        adminRole.setUser(admin);
        adminRole.setRole("ROLE_ADMIN");
        userRolesRepository.save(adminRole);

        UserRoles adminSecondRole = new UserRoles();
        adminSecondRole.setUser(admin);
        adminSecondRole.setRole("ROLE_USER");
        userRolesRepository.save(adminSecondRole);
    }
}
