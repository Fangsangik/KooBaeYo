package com.example.koobaeyo.auth.controller;

import com.example.koobaeyo.auth.exception.AuthBaseException;
import com.example.koobaeyo.auth.exception.type.AuthErrorCode;
import com.example.koobaeyo.common.CommonResponse;
import com.example.koobaeyo.auth.dto.LoginRequestDto;
import com.example.koobaeyo.auth.service.AuthService;
import com.example.koobaeyo.common.constants.Auth;
import com.example.koobaeyo.user.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.jar.Attributes;

@RestController
@RequestMapping
public class AuthController {

    private final AuthService loginService;

    @Autowired
    public AuthController(AuthService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<Void>> loginUser(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if(session == null) {
            session = request.getSession();
        }else{
            Object attribute = session.getAttribute(Auth.LOGIN_USER);
            User logined = (User) attribute;

            if(!logined.getEmail().equals(loginRequestDto.getEmail())){
                throw new AuthBaseException(AuthErrorCode.CAN_NOT_LOGIN);
            }
        }

        User user =  loginService.loginUser(loginRequestDto);

        session.setAttribute(Auth.LOGIN_USER, user);

        return new ResponseEntity<>(new CommonResponse<>("로그인 성공했습니다."), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<CommonResponse<Void>> logoutUser(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if(session !=null) {
            session.invalidate();
        }

        return new ResponseEntity<>(new CommonResponse<>("로그아웃 성공"),HttpStatus.OK);
    }


}

