package com.dano.clonedano.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UserSignUpRequestDto {

    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일을 입력해 주세요")
    @Pattern(regexp = "^[a-z0-9A-Z._-]*@[a-z0-9A-Z]*[.][a-zA-Z.]*$", message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "패스워드를 입력해 주세요")
    private String password;

    @NotBlank(message = "아이디를 입력해 주세요")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,16}$", message = "아이디는 알파벳 소문자와 숫자, 4~16자리수만 가능합니다.")
    private String userName;

    private String nickName;

    @Pattern(regexp = "^\\d{3}[.-]?\\d{4}[.-]?\\d{4}$", message = "휴대폰 전화번호 형식이 아닙니다.")
    private String phone;
}
