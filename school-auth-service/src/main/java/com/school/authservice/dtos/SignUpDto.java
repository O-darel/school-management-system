package com.school.authservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {

    private String name;
    private String email;
    private String password;
}
