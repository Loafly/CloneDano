package com.dano.clonedano.service;

import com.dano.clonedano.dto.UserLoginRequestDto;
import com.dano.clonedano.dto.UserRequestDto;
import com.dano.clonedano.dto.UserResponseDto;
import com.dano.clonedano.dto.UserSignUpRequestDto;
import com.dano.clonedano.model.User;
import com.dano.clonedano.repository.UserRepository;
import com.dano.clonedano.security.JwtTokenProvider;
import com.dano.clonedano.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public Long userSignUp(UserSignUpRequestDto userRequestDto){
        User user = User.builder()
                .userName(userRequestDto.getUserName())
                .nickName(userRequestDto.getNickName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .phone(userRequestDto.getPhone())
                .build();

        userRepository.save(user);

        return user.getUserId();
    }

    public String createToken(UserLoginRequestDto userLoginRequestDto){
        User user = userRepository.findByUserName(userLoginRequestDto.getUserName()).orElse(null);
        return jwtTokenProvider.createToken(user.getUserId());
    }

    public UserResponseDto getUser(UserDetailsImpl userDetails){

        User user = userDetails.getUser();

        return new UserResponseDto(
                user.getUserName(),
                user.getEmail(),
                user.getNickName(),
                user.getPhone()
        );
    }

    public Long modifyUser(UserDetailsImpl userDetails, UserRequestDto userRequestDto){
        User user = userDetails.getUser();

        user.setEmail(userRequestDto.getEmail());
        user.setNickName(userRequestDto.getNickName());
        user.setPhone(userRequestDto.getPhone());

        userRepository.save(user);

        return user.getUserId();
    }

    public void deleteUser(UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        userRepository.delete(user);
    }

}
