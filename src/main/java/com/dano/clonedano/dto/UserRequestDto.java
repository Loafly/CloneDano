package com.dano.clonedano.dto;

import lombok.Data;

@Data
public class UserRequestDto {

    private String password;

    private String email;

    private String phone;
}
