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


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

    public FindUserResponseDto findUser(Long id) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        return new FindUserResponseDto(findUser.getId(), findUser.getName(), findUser.getEmail(), findUser.getRole());
    }

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


    @Transactional
    public void deleteUser(Long id, String password) {

        User findUser = userRepository.findByIdOrElseThrow(id);

        if(!id.equals(findUser.getId())) {
            throw new UserBaseException(UserErrorCode.USE_PROPER_ID);
        }

        if(passwordEncoder.matches(password, findUser.getPassword())){
            findUser.setIsDeleted(true);
        } else {
            throw new UserBaseException(UserErrorCode.PASSWORD_DOES_NOT_MATCH);
        }


    }
}
