package com.school.authservice.controllers;


import com.school.authservice.dtos.LoginDto;
import com.school.authservice.dtos.LoginResponseDTO;
import com.school.authservice.dtos.SignUpDto;
import com.school.authservice.dtos.SignUpResponseDto;
import com.school.authservice.entities.Role;
import com.school.authservice.entities.RoleEnum;
import com.school.authservice.entities.User;
import com.school.authservice.respositories.RoleRepository;
import com.school.authservice.respositories.UserRepository;
import com.school.authservice.services.AuthenticationService;
import com.school.authservice.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthenticationController(
            AuthenticationService authenticationService,
            JwtService jwtService,
            UserRepository userRepository
    ){
        this.authenticationService=authenticationService;
        this.jwtService=jwtService;
        this.userRepository=userRepository;
    }

    //teacher sign up route
    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<SignUpResponseDto> adminSignupRoute(SignUpDto signUpDto){
        //register user
        User registeredUser= authenticationService.adminSignUp(signUpDto);

        //response
        SignUpResponseDto signUpResponse=new SignUpResponseDto();
        signUpResponse.setEmail(registeredUser.getEmail());
        signUpResponse.setName(registeredUser.getName());
        signUpResponse.setMessage("Successfully signed up");
        //return response
        return ResponseEntity.ok(signUpResponse);
    }



    //teacher sign up route
    @PostMapping("/register/teacher")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<SignUpResponseDto> teacherSignUpRoute(SignUpDto signUpDto){
        //register user
        User registeredUser= authenticationService.teacherSignUp(signUpDto);

        //response
        SignUpResponseDto signUpResponse=new SignUpResponseDto();
        signUpResponse.setEmail(registeredUser.getEmail());
        signUpResponse.setName(registeredUser.getName());
        signUpResponse.setMessage("Successfully signed up");
        //return response
        return ResponseEntity.ok(signUpResponse);
    }


    //student sign up route
    @PostMapping("/register/student")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','TEACHER')")
    public ResponseEntity<SignUpResponseDto> studentSignUpRoute(SignUpDto signUpDto){
        //register user
        User registeredUser= authenticationService.studentSignUp(signUpDto);

        //response
        SignUpResponseDto signUpResponse=new SignUpResponseDto();
        signUpResponse.setEmail(registeredUser.getEmail());
        signUpResponse.setName(registeredUser.getName());
        signUpResponse.setMessage("Successfully signed up");
        //return response
        return ResponseEntity.ok(signUpResponse);
    }


    //LOGIN ROUTE
    //PUBLIC
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> loginRoute(@RequestBody LoginDto loginDto){
        //register user
        User authenticatedUser= authenticationService.authenticate(loginDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        //response
        LoginResponseDTO loginResponse=new LoginResponseDTO();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        //return response
        return ResponseEntity.ok(loginResponse);
    }


    @GetMapping("/users/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }


}
