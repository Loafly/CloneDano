package com.dano.clonedano.service;

import com.dano.clonedano.dto.UserLoginRequestDto;
import com.dano.clonedano.dto.UserSignUpRequestDto;
import com.dano.clonedano.model.User;
import com.dano.clonedano.repository.UserRepository;
import com.dano.clonedano.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Long userSignUp(UserSignUpRequestDto userRequestDto){
        User user = User.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .phone(userRequestDto.getPhone())
                .build();

        userRepository.save(user);

        return user.getUserId();
    }

    public String createToken(UserLoginRequestDto userLoginRequestDto){
        User user = userRepository.findByName(userLoginRequestDto.getName()).orElse(null);
        return jwtTokenProvider.createToken(user.getUserId());
    }

}
