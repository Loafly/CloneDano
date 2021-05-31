package com.dano.clonedano.service;

import com.dano.clonedano.dto.UserSignUpRequestDto;
import com.dano.clonedano.model.User;
import com.dano.clonedano.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void userSignUp() {
        //given
        UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto();
        userSignUpRequestDto.setUserName("testUserName");
        userSignUpRequestDto.setPassword("1234");
        userSignUpRequestDto.setNickName("testNickName");
        userSignUpRequestDto.setPhone("010-1234-5678");

        //when
        Long userId = userService.userSignUp(userSignUpRequestDto);

        //then
        User findUser = userRepository.findById(userId).get();

        assertEquals(userSignUpRequestDto.getUserName(), findUser.getUserName());
        assertEquals(userSignUpRequestDto.getNickName(), findUser.getNickName());
        assertEquals(userSignUpRequestDto.getPhone(), findUser.getPhone());
    }

    @Test
    void createToken() {
    }

    @Test
    void getUser() {
    }

    @Test
    void modifyUser() {
    }

    @Test
    void deleteUser() {
    }
}