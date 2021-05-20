package com.dano.clonedano.controller;

import com.dano.clonedano.dto.UserLoginRequestDto;
import com.dano.clonedano.dto.UserRequestDto;
import com.dano.clonedano.dto.UserResponseDto;
import com.dano.clonedano.dto.UserSignUpRequestDto;
import com.dano.clonedano.security.UserDetailsImpl;
import com.dano.clonedano.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity userLogin(@Valid @RequestBody UserLoginRequestDto userLoginRequestDto, Errors errors) {

        if (errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.getAllErrors().get(0).getDefaultMessage());
        }

        try{
            return ResponseEntity.ok(userService.createToken(userLoginRequestDto));
        } catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    //회원 가입
    @PostMapping("/api/user/signup")
    public ResponseEntity registerUsers(@Valid @RequestBody UserSignUpRequestDto userSignUpRequestDto, Errors errors) {

        if (errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.getAllErrors().get(0).getDefaultMessage());
        }

        try{
            return ResponseEntity.ok(userService.userSignUp(userSignUpRequestDto));
        } catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    //회원수정
    @PutMapping("/api/user")
    public ResponseEntity modifyUser(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @RequestBody UserRequestDto userRequestDto, Errors errors){

        if(errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.getAllErrors().get(0).getDefaultMessage());
        }

        return ResponseEntity.ok(userService.modifyUser(userDetails, userRequestDto));
    }

    @DeleteMapping("/api/user")
    public void deleteUser(@AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.deleteUser(userDetails);
    }

}
