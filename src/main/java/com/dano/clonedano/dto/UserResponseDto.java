package com.dano.clonedano.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {

    private String userName;

    private String email;

    private String nickName;

    private String phone;
}
