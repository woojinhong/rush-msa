package com.order.userservice.service;

import com.order.userservice.client.OrderServiceClient;
import com.order.userservice.dto.OrderClientResponseDto;
import com.order.userservice.dto.UserOrderResponseDto;
import com.order.userservice.dto.UserSignUpRequestDto;
import com.order.userservice.entity.Users;
import com.order.userservice.repository.UserRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderServiceClient orderServiceClient;


    @Override
    public UserOrderResponseDto getUserByUserId(long userId) {
        Users user = userRepository.findByUserId(userId).orElseThrow(()->
                new IllegalArgumentException("해당 유저를 찾을 수 없습니다"));

        UserOrderResponseDto userResponseDto = UserOrderResponseDto.toDto(user);

        // 특정 유저의 주문 정보
        List<OrderClientResponseDto> ordersList = orderServiceClient.getOrdersByUserId(userId);

        // 주문 정보 저장
        userResponseDto.setOrders(ordersList);

        return userResponseDto;
    }

    @Transactional
    public void signUp(UserSignUpRequestDto userSignUpDto) {


        String pwdEncode = passwordEncoder.encode(userSignUpDto.getPassword());


        // 이메일 중복 체크
        validateDuplicateEmail(userSignUpDto.getEmail());

        // dto -> entity
        Users user = Users.toEntity(userSignUpDto, pwdEncode);


        userRepository.save(user);
    }

    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }
    }
}
