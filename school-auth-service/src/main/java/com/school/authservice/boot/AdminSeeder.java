package com.school.authservice.boot;
import com.school.authservice.dtos.SignUpDto;
import com.school.authservice.entities.Role;
import com.school.authservice.entities.RoleEnum;
import com.school.authservice.entities.User;
import com.school.authservice.respositories.RoleRepository;
import com.school.authservice.respositories.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.management.relation.RoleResult;
import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.roleRepository=roleRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){
        this.createSuperAdmin();
    }

    public  void createSuperAdmin(){
        SignUpDto userDto = new SignUpDto();
        userDto.setName("Super Admin");
        userDto.setEmail("super.admin@email.com");
        userDto.setPassword("Test@12345");

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = new User();
        user .setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(optionalRole.get());

        userRepository.save(user);
    }
}

