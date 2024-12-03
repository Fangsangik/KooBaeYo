package com.example.koobaeyo.login.controller;

import com.example.koobaeyo.common.CommonResponse;
import com.example.koobaeyo.login.dto.LoginRequestDto;
import com.example.koobaeyo.login.service.LoginService;
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

@RestController
@RequestMapping("logins")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<Void>> loginUser(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        //로그인을 성공하면 -> 세션에 회원정보를 저장해야되잖아요
        User user =  loginService.loginUser(loginRequestDto);

        HttpSession session = request.getSession();
        session.setAttribute("loginUser", user);
        return new ResponseEntity<>(new CommonResponse<>("성공했습니다."), HttpStatus.OK);
    }
}

