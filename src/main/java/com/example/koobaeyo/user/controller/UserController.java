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
import jakarta.validation.Valid;
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

    /**
     * 회원가입
     * @param requestDto - 회원가입을 위한 요청정보
     * @return SignUpResponseDto - 회원가입 완료 응답 dto
     */
    @PostMapping("/sign-up")
    public ResponseEntity<CommonResponse<SignUpResponseDto>> signup(@Valid @RequestBody SignUpRequestDto requestDto) {

        SignUpResponseDto signUpResponseDto =
                userService.signUp(requestDto);

        return new ResponseEntity<>(new CommonResponse<>("성공했습니다.", signUpResponseDto), HttpStatus.OK);
    }

    /**
     * 회원 단건 조회
     * @param id 회원 조회시 필요한 사용자 식별 id
     * @return FindUserResponseDto - 회원조회에 대한 응답 dto
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<CommonResponse<FindUserResponseDto>> findUser(@PathVariable Long id) {

        FindUserResponseDto findUserResponseDto =
                userService.findUser(id);

        return new ResponseEntity<>(new CommonResponse<>("조회 결과입니다", findUserResponseDto), HttpStatus.OK);
    }

    /**
     * 회원 수정
     * @param requestDto 회원 수정을 위한 요청정보
     * @param request 세션 생성을 위한 HTTP요청
     * @return UpdateUserResponseDto - 회원수정에 대한 응답 dto
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<CommonResponse<UpdateUserResponseDto>> updateUser(@RequestBody UpdateUserRequestDto requestDto, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute(Auth.LOGIN_USER);

        UpdateUserResponseDto updateUserResponseDto =
                userService.updateUser(user.getId(), requestDto);

        return new ResponseEntity<>(new CommonResponse<>("정상적으로 수정되었습니다", updateUserResponseDto), HttpStatus.CREATED);
    }

    /**
     * 회원 삭제
     * @param id 회원 삭제시 필요한 사용자 고유 식별 id
     * @param deleteUserRequestDto 회원 삭제시 필요한
     * @param request 세션 생성을 위한 HTTP요청
     * @return 없음 / 회원삭제 성공 메세지
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteUser(@PathVariable Long id, @RequestBody DeleteUserRequestDto deleteUserRequestDto, HttpServletRequest request) {

        userService.deleteUser(id, deleteUserRequestDto.getPassword());

        HttpSession session = request.getSession(false);
        session.getAttribute(Auth.LOGIN_USER);

        session.invalidate();

        return new ResponseEntity<>(new CommonResponse<>("정상적으로 삭제되었습니다."), HttpStatus.OK);
    }
}
