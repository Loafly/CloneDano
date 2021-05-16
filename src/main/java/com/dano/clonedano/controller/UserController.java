package com.dano.clonedano.controller;

import com.dano.clonedano.dto.UserRequestDto;
import com.dano.clonedano.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원 가입
    @PostMapping("/api/user/signup")
    public Long registerUsers(@RequestBody UserRequestDto userRequestDto) {
            return userService.userSignUp(userRequestDto);
    }
}
