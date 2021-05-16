package com.dano.clonedano.service;

import com.dano.clonedano.dto.UserRequestDto;
import com.dano.clonedano.model.User;
import com.dano.clonedano.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Long userSignUp(UserRequestDto userRequestDto){
        User user = User.builder()
                .name(userRequestDto.getName())
                .email(userRequestDto.getEmail())
                .password(userRequestDto.getPassword())
                .phone(userRequestDto.getPhone())
                .build();

        userRepository.save(user);

        return user.getUserId();

    }

}
