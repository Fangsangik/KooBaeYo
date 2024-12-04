package com.example.koobaeyo.user.controller;


import com.example.koobaeyo.common.CommonResponse;
import com.example.koobaeyo.user.dto.finduser.FindUserResponseDto;
import com.example.koobaeyo.user.dto.signup.SignUpRequestDto;
import com.example.koobaeyo.user.dto.signup.SignUpResponseDto;
import com.example.koobaeyo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<CommonResponse<SignUpResponseDto>>signup(@RequestBody SignUpRequestDto requestDto) {

        SignUpResponseDto signUpResponseDto =
                userService.signUp(requestDto);

        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.", signUpResponseDto), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<CommonResponse<FindUserResponseDto>>findUser(@PathVariable Long id) {

        FindUserResponseDto findUserResponseDto =
                userService.findUser(id);

        return new ResponseEntity<>(new CommonResponse<>("조회 결과입니다", findUserResponseDto), HttpStatus.OK);
    }
}
