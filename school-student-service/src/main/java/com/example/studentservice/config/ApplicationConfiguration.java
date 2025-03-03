package com.example.studentservice.config;

import com.example.studentservice.client.AuthServiceClient;
import com.example.studentservice.dto.UserDto;
import com.example.studentservice.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity(debug = true)
public class ApplicationConfiguration {

    //private final AuthServiceClient authServiceClient;
    private final UserRepository userRepository;

    public ApplicationConfiguration(
            //AuthServiceClient authServiceClient
            UserRepository userRepository
    ){
        //this.authServiceClient=authServiceClient;
        this.userRepository=userRepository;

    }
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

//        return username -> {
//            UserDto user = authServiceClient.getUserByEmail(username);
//
//            if (user == null) {
//                throw new UsernameNotFoundException("User not found");
//            }
//
//            return User.builder()
//                    .username(user.getEmail())
//                    .password(user.getPassword()) // Ensure password is hashed
//                    .roles(user.getRole()) // Assuming role is stored as a String
//                    .build();
//        };
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

}
