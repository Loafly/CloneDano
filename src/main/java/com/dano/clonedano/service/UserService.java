package com.dano.clonedano.service;

import com.dano.clonedano.dto.*;
import com.dano.clonedano.exception.BadRequestException;
import com.dano.clonedano.model.Cart;
import com.dano.clonedano.model.Order;
import com.dano.clonedano.model.User;
import com.dano.clonedano.repository.CartRepository;
import com.dano.clonedano.repository.OrderRepository;
import com.dano.clonedano.repository.UserRepository;
import com.dano.clonedano.security.JwtTokenProvider;
import com.dano.clonedano.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long userSignUp(UserSignUpRequestDto userRequestDto){

        Optional<User> optionalUser = userRepository.findByUserName(userRequestDto.getUserName());

        if (optionalUser.isPresent()){
            throw new IllegalArgumentException("이미 가입된 ID가 있습니다.");
        }

        String password = bCryptPasswordEncoder.encode(userRequestDto.getPassword());

        User user = User.builder()
                .userName(userRequestDto.getUserName())
                .nickName(userRequestDto.getNickName())
                .email(userRequestDto.getEmail())
                .password(password)
                .phone(userRequestDto.getPhone())
                .build();

        userRepository.save(user);

        return user.getUserId();
    }

    public String createToken(UserLoginRequestDto userLoginRequestDto){

        User user = userRepository.findByUserName(userLoginRequestDto.getUserName()).orElseThrow(
                () -> new BadRequestException("가입되지 않은 아이디입니다."));

        if (!bCryptPasswordEncoder.encode(userLoginRequestDto.getPassword()).equals(user.getPassword())){
            throw new BadRequestException("잘못된 비밀번호 입니다.");
        }

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

    @Transactional
    public UserResponseDto modifyUser(UserDetailsImpl userDetails, UserRequestDto userRequestDto){
        User user = userDetails.getUser();

        String password = bCryptPasswordEncoder.encode(userRequestDto.getPassword());

        user.setEmail(userRequestDto.getEmail());
        user.setPhone(userRequestDto.getPhone());
        user.setPassword(password);

        userRepository.save(user);

        return new UserResponseDto(
                user.getUserName(),
                user.getEmail(),
                user.getNickName(),
                user.getPhone()
        );
    }

    public void deleteUser(UserDetailsImpl userDetails){
        User user = userDetails.getUser();

        List<Order> orderList = orderRepository.findByUser(user);
        orderRepository.deleteAll(orderList);

        List<Cart> cartList = cartRepository.findByUser(user);
        cartRepository.deleteAll(cartList);

        userRepository.delete(user);
    }

}
