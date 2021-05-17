package com.dano.clonedano.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRequestDto {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}
