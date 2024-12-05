package com.example.koobaeyo.auth.service;

import com.example.koobaeyo.auth.dto.LoginRequestDto;
import com.example.koobaeyo.auth.exception.AuthBaseException;
import com.example.koobaeyo.auth.exception.type.AuthErrorCode;
import com.example.koobaeyo.common.config.PasswordEncoder;
import com.example.koobaeyo.user.entity.User;
import com.example.koobaeyo.user.exception.UserBaseException;
import com.example.koobaeyo.user.exception.code.UserErrorCode;
import com.example.koobaeyo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());

        if(user == null) {
            throw new AuthBaseException(AuthErrorCode.CANNOT_FIND_USER);
        }

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new AuthBaseException(AuthErrorCode.PASSWORD_DOES_NOT_MATCH);
        }

        if(user.getIsDeleted() == true) {
            throw new AuthBaseException(AuthErrorCode.DELETED_USER);
        }

        return user;
    }
}
