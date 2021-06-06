package com.dano.clonedano.service;

import com.dano.clonedano.dto.UserLoginRequestDto;
import com.dano.clonedano.dto.UserRequestDto;
import com.dano.clonedano.dto.UserResponseDto;
import com.dano.clonedano.dto.UserSignUpRequestDto;
import com.dano.clonedano.exception.BadRequestException;
import com.dano.clonedano.model.User;
import com.dano.clonedano.repository.UserRepository;
import com.dano.clonedano.security.JwtTokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto();

    @BeforeEach
    public void setUp(){
        //회원 등록
        userSignUpRequestDto.setUserName("userName");
        userSignUpRequestDto.setPassword("1234");
        userSignUpRequestDto.setNickName("nickName");
        userSignUpRequestDto.setPhone("010-1234-5678");
        userSignUpRequestDto.setEmail("email@naver.com");

        userService.userSignUp(userSignUpRequestDto);
    }

    @AfterEach
    public void tearDown(){
        userRepository.deleteAll();
    }


    @Test
    @DisplayName("회원가입 - 성공")
    void userSignUpSuccess() {
        //given
        UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto();
        userSignUpRequestDto.setUserName("testUserName");
        userSignUpRequestDto.setPassword("1234");
        userSignUpRequestDto.setNickName("testNickName");
        userSignUpRequestDto.setPhone("010-1234-5678");
        userSignUpRequestDto.setEmail("test@naver.com");

        //when
        Long userId = userService.userSignUp(userSignUpRequestDto);

        //then
        User findUser = userRepository.findById(userId).get();

        assertEquals(userSignUpRequestDto.getUserName(), findUser.getUserName());
        assertEquals(userSignUpRequestDto.getNickName(), findUser.getNickName());
        assertEquals(userSignUpRequestDto.getPhone(), findUser.getPhone());
        assertEquals(userSignUpRequestDto.getEmail(), findUser.getEmail());
    }

    @Test
    @DisplayName("회원가입 - 실패")
    void userSignUpFail() {
        //given
        UserSignUpRequestDto userSignUpRequestDto = new UserSignUpRequestDto();
        userSignUpRequestDto.setUserName("testUserName");
        userSignUpRequestDto.setPassword("1234");
        userSignUpRequestDto.setNickName("testNickName");
        userSignUpRequestDto.setPhone("010-1234-5678");
        userSignUpRequestDto.setEmail("test@naver.com");

        //when
        userService.userSignUp(userSignUpRequestDto);

        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> userService.userSignUp(userSignUpRequestDto));

        //then
        assertEquals("이미 가입된 ID가 있습니다.",illegalArgumentException.getMessage());
    }

    @Test
    @DisplayName("로그인 - 성공")
    void createTokenSuccess() {
        //given
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setUserName(userSignUpRequestDto.getUserName());
        userLoginRequestDto.setPassword(userSignUpRequestDto.getPassword());

        //when
        String token = userService.createToken(userLoginRequestDto);

        //then
        assertTrue(jwtTokenProvider.getAuthentication(token).isAuthenticated());
    }

    @Test
    @DisplayName("로그인 - 실패 가입되지 않은 아이디")
    void createTokenIdFail() {
        //given
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setUserName("NoUserName");
        userLoginRequestDto.setPassword("5678");

        //when
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> userService.createToken(userLoginRequestDto));

        //then
        assertEquals("가입되지 않은 아이디입니다.",badRequestException.getMessage());
    }

    @Test
    @DisplayName("로그인 - 실패 잘못된 비밀번호")
    void createTokenPasswordFail() {
        //given
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setUserName(userSignUpRequestDto.getUserName());
        userLoginRequestDto.setPassword("5678");

        //when
        BadRequestException badRequestException = assertThrows(BadRequestException.class, () -> userService.createToken(userLoginRequestDto));

        //then
        assertEquals("잘못된 비밀번호 입니다.",badRequestException.getMessage());
    }

    @Test
    void getUser() {
        //given
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setUserName(userSignUpRequestDto.getUserName());
        userLoginRequestDto.setPassword(userSignUpRequestDto.getPassword());
        String token = userService.createToken(userLoginRequestDto);

        //when
        UserResponseDto userResponseDto = userService.getUser(jwtTokenProvider.getAuthenticationUserDetailsImpl(token));

        //then
        assertEquals(userSignUpRequestDto.getUserName(), userResponseDto.getUserName());
        assertEquals(userSignUpRequestDto.getEmail(), userResponseDto.getEmail());
        assertEquals(userSignUpRequestDto.getNickName(), userResponseDto.getNickName());
        assertEquals(userSignUpRequestDto.getPhone(), userResponseDto.getPhone());
    }

    @Test
    void modifyUser() {
        //given
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setEmail("modifyEmail@naver.com");
        userRequestDto.setPhone("010-8765-4321");
        userRequestDto.setPassword("4321");

        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setUserName(userSignUpRequestDto.getUserName());
        userLoginRequestDto.setPassword(userSignUpRequestDto.getPassword());
        String token = userService.createToken(userLoginRequestDto);

        //when
        UserResponseDto userResponseDto = userService.modifyUser(jwtTokenProvider.getAuthenticationUserDetailsImpl(token), userRequestDto);

        //then
        assertEquals(userRequestDto.getEmail(), userResponseDto.getEmail());
        assertEquals(userRequestDto.getPhone(), userResponseDto.getPhone());
    }

    @Test
    void deleteUser() {
        //given
        UserLoginRequestDto userLoginRequestDto = new UserLoginRequestDto();
        userLoginRequestDto.setUserName(userSignUpRequestDto.getUserName());
        userLoginRequestDto.setPassword(userSignUpRequestDto.getPassword());
        String token = userService.createToken(userLoginRequestDto);

        //when
        userService.deleteUser(jwtTokenProvider.getAuthenticationUserDetailsImpl(token));

        //then
        assertNull(userRepository.findByUserName(userLoginRequestDto.getUserName()).orElse(null));
    }
}