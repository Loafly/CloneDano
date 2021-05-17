package com.dano.clonedano.controller;

import com.dano.clonedano.dto.UserLoginRequestDto;
import com.dano.clonedano.dto.UserRequestDto;
import com.dano.clonedano.dto.UserResponseDto;
import com.dano.clonedano.dto.UserSignUpRequestDto;
import com.dano.clonedano.security.UserDetailsImpl;
import com.dano.clonedano.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //로그인된 사용자 정보
    @GetMapping("/api/user")
    public UserResponseDto getUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return userService.getUser(userDetails);
    }

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

    //회원수정
    @PutMapping("/api/user")
    public Long modifyUser(@AuthenticationPrincipal UserDetailsImpl userDetails, UserRequestDto userRequestDto){
        return userService.modifyUser(userDetails, userRequestDto);
    }

    @DeleteMapping("/api/user")
    public void deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.deleteUser(userDetails);
    }

}
