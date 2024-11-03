package com.order.userservice.service;

import com.order.userservice.dto.UserSignUpRequestDto;
import com.order.userservice.entity.Users;
import com.order.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void signUp(UserSignUpRequestDto userSignUpDto) {

        //dto -> entity

        //String pwdEncode = passwordEncoder.encode(userSignUpDto.getPassword());

        String pwdEncode = "qwerorqwop";

        // 이메일 중복 체크
        validateDuplicateEmail(userSignUpDto.getEmail());

        // dto -> entity
        Users users = Users.toEntity(userSignUpDto, pwdEncode);

        userRepository.save(users);
    }

    private void validateDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }
    }
}
