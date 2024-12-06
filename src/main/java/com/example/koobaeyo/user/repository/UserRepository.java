package com.example.koobaeyo.user.repository;

import com.example.koobaeyo.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    /**
     * 사용자 고유식별 id로 해당하는 사용자 찾기
     * @param id 사용자 고유식별 아이디
     * @return id로 찾은 해당하는 User반환
     */
    default User findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}

