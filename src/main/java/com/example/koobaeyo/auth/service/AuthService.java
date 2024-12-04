package com.example.koobaeyo.auth.service;

import com.example.koobaeyo.auth.dto.LoginRequestDto;
import com.example.koobaeyo.user.entity.User;
import com.example.koobaeyo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;

    @Autowired
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());

        if(user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        if (!loginRequestDto.getPassword().equals(user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "잘못된 비밀번호입니다.");
        }

        return user;

        //1. dto에 있는 이메일이 이미 DB에 존재하는지 확인
        //DB에 존재하면 user가져오기
        //2. 가져온 user의 패스워드와 dto의 패스워드 비교
        //틀리면 예외
        //맞으면 user 가져와서 리턴
    }


}
