package com.dano.clonedano.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginRequestDto {

    @NotBlank(message = "아이디를 입력해 주세요.")
    private String userName;

    @NotBlank(message = "패스워드를 입력해 주세요.")
    private String password;
}
