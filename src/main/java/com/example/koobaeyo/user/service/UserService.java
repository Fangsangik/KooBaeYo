package com.example.koobaeyo.user.service;

import com.example.koobaeyo.common.config.PasswordEncoder;
import com.example.koobaeyo.common.util.UtilValidation;
import com.example.koobaeyo.user.dto.finduser.FindUserResponseDto;
import com.example.koobaeyo.user.dto.signup.SignUpRequestDto;
import com.example.koobaeyo.user.dto.signup.SignUpResponseDto;
import com.example.koobaeyo.user.dto.update.UpdateUserRequestDto;
import com.example.koobaeyo.user.dto.update.UpdateUserResponseDto;
import com.example.koobaeyo.user.entity.User;
import com.example.koobaeyo.user.exception.UserBaseException;
import com.example.koobaeyo.user.exception.code.UserErrorCode;
import com.example.koobaeyo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원가입
     * @param requestDto 회원가입을 위한 요청정보
     * @return SignUpResponseDto - 회원가입 완료 응답 dto
     */
    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
        if (!UtilValidation.isValidPasswordFormat(requestDto.getPassword())) {
            throw new UserBaseException(UserErrorCode.IMPROPER_PASSWORD);
        }

        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());

        User userByEmail = userRepository.findByEmail(requestDto.getEmail());

        if (userByEmail != null) {
            throw new UserBaseException(UserErrorCode.DUPLICATED_EMAIL);
        }

        User savedUser = userRepository.save(requestDto.toEntity(hashedPassword));

        return new SignUpResponseDto(savedUser.getId());
    }

    /**
     * 회원 단건 조회
     * @param id 회원 조회시 필요한 사용자 식별 id
     * @return FindUserResponseDto - 회원조회에 대한 응답 dto
     */
    public FindUserResponseDto findUser(Long id) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        return new FindUserResponseDto(findUser.getId(), findUser.getName(), findUser.getEmail(), findUser.getRole());
    }

    /**
     * 회원 수정
     * @param id 회원정보 수정시 필요한 사용자 식별 id
     * @param requestDto 회원정보 수정을 위한 요청정보
     * @return UpdateUserResponseDto - 회원수정에 대한 응답 dto
     */
    @Transactional
    public UpdateUserResponseDto updateUser(Long id, UpdateUserRequestDto requestDto) {


        User findUser = userRepository.findById(id)
                .orElseThrow(() -> new UserBaseException(UserErrorCode.CANNOT_FIND_ID));


        if (!UtilValidation.isValidPasswordFormat(requestDto.getNewPassword())) {
            throw new UserBaseException(UserErrorCode.IMPROPER_PASSWORD);
        }

        String hashedPassword = passwordEncoder.encode(requestDto.getNewPassword());

        findUser.setName(requestDto.getNewName());
        findUser.setEmail(requestDto.getNewEmail());
        findUser.setPassword(hashedPassword);

        User updateUser = userRepository.save(findUser);

        return new UpdateUserResponseDto(updateUser.getId(), updateUser.getName(), updateUser.getEmail());
    }


    /**
     * 회원 삭제
     * @param id 회원 삭제시 필요한 사용자 고유 식별 id
     * @param password 회원 삭제시 필요한 사용자 비밀번호
     */
    @Transactional
    public void deleteUser(Long id, String password) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        if(!id.equals(findUser.getId())) {
            throw new UserBaseException(UserErrorCode.USE_PROPER_ID);
        }

        if(passwordEncoder.matches(password, findUser.getPassword())){
            findUser.setDeleted(true);
            findUser.setDeletedAt(LocalDateTime.now());
        } else {
            throw new UserBaseException(UserErrorCode.PASSWORD_DOES_NOT_MATCH);
        }


    }
}
