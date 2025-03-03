package com.school.authservice.services;

import com.school.authservice.dtos.LoginDto;
import com.school.authservice.dtos.SignUpDto;
import com.school.authservice.entities.Role;
import com.school.authservice.entities.RoleEnum;
import com.school.authservice.entities.User;
import com.school.authservice.respositories.RoleRepository;
import com.school.authservice.respositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager
    ){
        this.roleRepository=roleRepository;
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
        this.authenticationManager=authenticationManager;
    }

    //admin sign up service
    public User adminSignUp(SignUpDto signUpDto){
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);

        if (optionalRole.isEmpty()) {
            return null;
        }

        User user=new User();
        user.setEmail(signUpDto.getEmail());
        user.setName(signUpDto.getName());
        user.setRole(optionalRole.get());
        //encoding password before storing
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        //save
        return userRepository.save(user);
    }

    //teacher sign up
    public User teacherSignUp(SignUpDto signUpDto){
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.TEACHER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        User user=new User();
        user.setEmail(signUpDto.getEmail());
        user.setName(signUpDto.getName());
        user.setRole(optionalRole.get());
        //encoding password before storing
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        //save
        return userRepository.save(user);
    }

    //student sign up
    public User studentSignUp(SignUpDto signUpDto){
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.STUDENT);

        if (optionalRole.isEmpty()) {
            return null;
        }

        User user=new User();
        user.setEmail(signUpDto.getEmail());
        user.setName(signUpDto.getName());
        user.setRole(optionalRole.get());
        //encoding password before storing
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        //save
        return userRepository.save(user);
    }




    //login service
    public User authenticate(LoginDto loginDto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getEmail(),
                        loginDto.getPassword()
                )
        );

        return userRepository.findByEmail(loginDto.getEmail())
                .orElseThrow();
    }

}


