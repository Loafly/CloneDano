package com.dano.clonedano.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UserRequestDto {
    private String name;
    private String password;

    @Email
    private String email;
    private String phone;
}
