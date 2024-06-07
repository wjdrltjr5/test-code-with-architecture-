package com.example.demo.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void User는_UserCreate_객체로_생성할_수_있다() {
        // given

        // when

        // then
    }
    @Test
    void User는_UserUpdate_객체로_데이터를_업데이트_할_수_있다() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("유저는 로그인 할 수 있고 로그인시 마지막 로그인 시간이 변경된다.")
    void login() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("유저는 유효한 인증 코드로 계정을 활성화 할 수 있다.")
    void certification() {
        // given

        // when

        // then
    }
    @Test
    @DisplayName("유저는 잘못된 인증 코드로 계정을 활성화 하려하면 에러를 던진다.")
    void certificationThrow() {
        // given

        // when

        // then
    }

}