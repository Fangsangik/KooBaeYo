package com.example.koobaeyo.user.controller;

import com.example.koobaeyo.common.CommonResponse;
import com.example.koobaeyo.common.constants.Auth;
import com.example.koobaeyo.user.dto.delete.DeleteUserRequestDto;
import com.example.koobaeyo.user.dto.finduser.FindUserResponseDto;
import com.example.koobaeyo.user.dto.signup.SignUpRequestDto;
import com.example.koobaeyo.user.dto.signup.SignUpResponseDto;
import com.example.koobaeyo.user.dto.update.UpdateUserRequestDto;
import com.example.koobaeyo.user.dto.update.UpdateUserResponseDto;
import com.example.koobaeyo.user.entity.User;
import com.example.koobaeyo.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<CommonResponse<SignUpResponseDto>> signup(@RequestBody SignUpRequestDto requestDto) {

        SignUpResponseDto signUpResponseDto =
                userService.signUp(requestDto);

        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.", signUpResponseDto), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<CommonResponse<FindUserResponseDto>> findUser(@PathVariable Long id) {

        FindUserResponseDto findUserResponseDto =
                userService.findUser(id);

        return new ResponseEntity<>(new CommonResponse<>("조회 결과입니다", findUserResponseDto), HttpStatus.OK);
    }

    // TODO: ID값 @pathvariable로 넣을지 결정
    @PutMapping("/users/{id}")
    public ResponseEntity<CommonResponse<UpdateUserResponseDto>> updateUser(@RequestBody UpdateUserRequestDto requestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Auth.LOGIN_USER);

        UpdateUserResponseDto updateUserResponseDto =
                userService.updateUser(user.getId(), requestDto);

        return new ResponseEntity<>(new CommonResponse<>("정상적으로 수정되었습니다", updateUserResponseDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteUser(@PathVariable Long id, @RequestBody DeleteUserRequestDto deleteUserRequestDto, HttpServletRequest request) {

        userService.deleteUser(id, deleteUserRequestDto.getPassword());

        HttpSession session = request.getSession(false);
        session.getAttribute(Auth.LOGIN_USER);

        session.invalidate();

        return new ResponseEntity<>(new CommonResponse<>("정상적으로 삭제되었습니다."), HttpStatus.OK);
    }
}
