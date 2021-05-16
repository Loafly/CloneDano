package com.dano.clonedano.controller;

import com.dano.clonedano.dto.UserLoginRequestDto;
import com.dano.clonedano.dto.UserSignUpRequestDto;
import com.dano.clonedano.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //로그인
    @PostMapping("/api/user/login")
    public String userLogin(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        return userService.createToken(userLoginRequestDto);
    }

    //회원 가입
    @PostMapping("/api/user/signup")
    public Long registerUsers(@RequestBody UserSignUpRequestDto userSignUpRequestDto) {
            return userService.userSignUp(userSignUpRequestDto);
    }
}
