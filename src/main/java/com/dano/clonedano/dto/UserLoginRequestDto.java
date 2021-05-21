package com.dano.clonedano.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserLoginRequestDto {

    @NotBlank(message = "아이디를 입력해 주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,16}$", message = "아이디는 알파벳 소문자와 숫자, 4~16자리수만 가능합니다.")
    private String userName;

    @NotBlank(message = "패스워드를 입력해 주세요.")
    private String password;
}
