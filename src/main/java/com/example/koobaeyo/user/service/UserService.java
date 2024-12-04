package com.example.koobaeyo.user.service;

import com.example.koobaeyo.common.util.UtilValidation;
import com.example.koobaeyo.user.dto.finduser.FindUserResponseDto;
import com.example.koobaeyo.user.dto.signup.SignUpRequestDto;
import com.example.koobaeyo.user.dto.signup.SignUpResponseDto;
import com.example.koobaeyo.user.dto.update.UpdateUserRequestDto;
import com.example.koobaeyo.user.dto.update.UpdateUserResponseDto;
import com.example.koobaeyo.user.entity.User;
import com.example.koobaeyo.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SignUpResponseDto signUp(
            SignUpRequestDto requestDto
    ) {
        if (!UtilValidation.isValidPasswordFormat(requestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "비밀번호는 최소 8자, 대소문자 포함한 영문, 숫자, 특수문자를 포함해야합니다.");
        }

        User userByEmail = userRepository.findByEmail(requestDto.getEmail());

        if (userByEmail != null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "중복된 이메일입니다.");
        }

        User savedUser = userRepository.save(requestDto.toEntity());

        return new SignUpResponseDto(savedUser.getId());
    }

    public FindUserResponseDto findUser(Long id) {

        User findUser = userRepository.FindByIdOrElseThrow(id);

        return new FindUserResponseDto(findUser.getId(), findUser.getName(), findUser.getEmail(), findUser.getRole());
    }
}
