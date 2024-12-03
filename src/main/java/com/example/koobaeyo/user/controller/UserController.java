package com.example.koobaeyo.user.controller;


import com.example.koobaeyo.user.dto.signup.SignUpRequestDto;
import com.example.koobaeyo.user.dto.signup.SignUpResponseDto;
import com.example.koobaeyo.user.dto.update.UpdateUserRequestDto;
import com.example.koobaeyo.user.dto.update.UpdateUserResponseDto;
import com.example.koobaeyo.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto>signup(@RequestBody SignUpRequestDto requestDto) {

        SignUpResponseDto signUpResponseDto =
                userService.signUp(requestDto);

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<UpdateUserResponseDto> updateUser(HttpServletRequest request, @RequestBody UpdateUserRequestDto requestDto) {
//        UpdateUserResponseDto responseDto = userService.updateUser(request, requestDto);
//
//        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
//    }

}
