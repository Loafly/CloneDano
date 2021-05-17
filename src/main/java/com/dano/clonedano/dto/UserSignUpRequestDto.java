package com.dano.clonedano.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserSignUpRequestDto {

    @Email
    @NotBlank
    private String email;
    
    @NotBlank
    private String password;

    @NotBlank
    private String userName;

    @NotBlank
    private String nickName;

    private String phone;
}
