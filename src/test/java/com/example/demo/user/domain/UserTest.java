package com.example.demo.user.domain;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void User는_UserCreate_객체로_생성할_수_있다() {
        // given
        UserCreate userCreate = UserCreate.builder()
                .email("wjdrltjr5@naver.com")
                .nickname("wjdrltjr5")
                .address("Busan")
                .build();
        // when
        User user = User.from(userCreate, new TestUuidHolder("aaaaaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaab"));
        // then

        assertThat(user.getEmail()).isEqualTo("wjdrltjr5@naver.com");
        assertThat(user.getId()).isNull();
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getNickname()).isEqualTo("wjdrltjr5");
        assertThat(user.getAddress()).isEqualTo("Busan");
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaab");
    }
    @Test
    void User는_UserUpdate_객체로_데이터를_업데이트_할_수_있다() {
        // given
        User user = User.builder()
                .id(1L)
                .email("wjdrltjr5@kakao.com")
                .nickname("wjdrltjr2")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaab")
                .build();


        UserUpdate userUpdate = UserUpdate.builder()
                .nickname("wjdrltjr3")
                .address("미국")
                .build();
        // when
        user = user.update(userUpdate);
        // then

        assertThat(user.getEmail()).isEqualTo("wjdrltjr5@kakao.com");
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE );
        assertThat(user.getNickname()).isEqualTo("wjdrltjr3");
        assertThat(user.getAddress()).isEqualTo("미국");
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaab");
    }

    @Test
    @DisplayName("유저는 로그인 할 수 있고 로그인시 마지막 로그인 시간이 변경된다.")
    void login() {
        // given
        User user = User.builder()
                .id(1L)
                .email("wjdrltjr5@kakao.com")
                .nickname("wjdrltjr5")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();

        // when
        user = user.login(new TestClockHolder(1678530673958L));

        // then
        assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    @DisplayName("유저는 유효한 인증 코드로 계정을 활성화 할 수 있다.")
    void certification() {
        // given
        User user = User.builder()
                .id(1L)
                .email("wjdrltjr5@kakao.com")
                .nickname("wjdrltjr5")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        // when
        user = user.certification("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        // then
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
    @Test
    @DisplayName("유저는 잘못된 인증 코드로 계정을 활성화 하려하면 에러를 던진다.")
    void certificationThrow() {
        // given
        User user = User.builder()
                .id(1L)
                .email("wjdrltjr5@kakao.com")
                .nickname("wjdrltjr5")
                .address("Seoul")
                .status(UserStatus.PENDING)
                .lastLoginAt(100L)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .build();
        // when
        // then
        assertThatThrownBy(() -> user.certification("aaaaaaaa-aaaa-aaaa-aaaa-awd")).isInstanceOf(
                CertificationCodeNotMatchedException.class
        );
    }

}