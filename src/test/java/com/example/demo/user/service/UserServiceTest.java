package com.example.demo.user.service;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.infrastructure.UserEntity;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql" , executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})

class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    @DisplayName("getByEmail은 Active 상태인 유저를 찾아올 수 있다.")
    void getByEmail() {
        // given
        String email = "wjdrltjr5@naver.com";
        // when
        UserEntity result = userService.getByEmail(email);
        // then
        assertThat(result.getNickname()).isEqualTo("wjdrltjr");

    }

    @Test
    @DisplayName("getByEmail은 Penfing 상태인 유저를 찾아올 수 없다.")
    void getByEmail_Pending() {
        // given
        String email = "wjdrltjr6@naver.com";
        // when

        // then
        assertThatThrownBy(() -> userService.getByEmail(email)).isInstanceOf(ResourceNotFoundException.class);

    }

    @Test
    @DisplayName("getById로 Active 사용자 찾아오기")
    void getByid() {
        // given

        // when
        UserEntity result = userService.getById(1);
        // then
        assertThat(result.getNickname()).isEqualTo("wjdrltjr");
    }
    @Test
    @DisplayName("getById로 Pending사용자는 찾을 수 없다.")
    void getByid_Pending() {
        // given
        // when
        // then
        assertThatThrownBy(() -> userService.getById(2)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("유저 생성")
    void create() {
        // given
        UserCreate dto = UserCreate.builder().email("wjdrltjr2@naver.com")
                .address("alrnr")
                .nickname("wjdrltjr2")
                .build();
        BDDMockito.doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));
        
        // when
        UserEntity result = userService.create(dto);
        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
        // assertThat(result.getCertificationCode()).isEqualTo("T.T");
    }
    
    @Test
    @DisplayName("User 수정하기")
    void userUpdateDto_를_이용하여_유저를_수정할_수_있다() {
        // given
        UserUpdate dto = UserUpdate.builder().address("Seoul")
                .nickname("awdawd").build();
        // when
        UserEntity result = userService.update(1,dto);
        // then
        UserEntity userEntity = userService.getById(1);
        assertThat(userEntity.getNickname()).isEqualTo(dto.getNickname());
        assertThat(userEntity.getAddress()).isEqualTo(dto.getAddress());
    }

    @Test
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
        // given
        // when
        userService.login(1);
        // then
        UserEntity userEntity = userService.getById(1);
        assertThat(userEntity.getLastLoginAt()).isGreaterThan(0);
    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
        // given
        // when
        userService.verifyEmail(2,"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        // then
        UserEntity userEntity = userService.getById(2);
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> userService.verifyEmail(2,"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaccaa"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}