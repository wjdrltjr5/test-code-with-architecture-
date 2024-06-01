package com.example.demo.repository;

import com.example.demo.model.UserStatus;
import org.h2.engine.User;
import org.hibernate.query.sqm.mutation.internal.cte.CteInsertStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@TestPropertySource("classpath:test-application.properties")
@Sql("/sql/user-repository-test-data.sql")
class UserRepositoryTest {


    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Id와 Status로 유저 데이터 찾기")
    void findByIdAndStatus_로_유저_데이터_찾기() {
        // given
        // when
        Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.ACTIVE);
        // then
        assertThat(result.isPresent()).isTrue();
    }
    
    @Test
    @DisplayName("유저 데이터가 없으면 옵셔널 엠프티를 내려준다.")
    void findByIdAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
        // given
        // when
        Optional<UserEntity> result = userRepository.findByIdAndStatus(1, UserStatus.PENDING);
        // then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("이메일과 Status로 유저 데이터 찾기")
    void findByEmailAndStatus_로_유저_데이터_찾기() {
        // given
        // when
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("wjdrltjr5@naver.com", UserStatus.ACTIVE);
        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("유저 데이터가 없으면 옵셔널 엠프티를 내려준다.")
    void findByEMailAndStatus_는_데이터가_없으면_Optional_empty_를_내려준다() {
        // given
        // when
        Optional<UserEntity> result = userRepository.findByEmailAndStatus("wjdrltjr5@naver.com", UserStatus.PENDING);
        // then
        assertThat(result.isEmpty()).isTrue();
    }

}