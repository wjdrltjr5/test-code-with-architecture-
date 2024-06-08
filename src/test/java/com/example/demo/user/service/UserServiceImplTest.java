package com.example.demo.user.service;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.FakeMailSender;
import com.example.demo.mock.FakeUserRepository;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @BeforeEach
    void init(){
        FakeMailSender fakeMailSender = new FakeMailSender();

        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.userServiceImpl = UserServiceImpl.builder()
                .uuidHolder(new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"))
                .certificationService(new CertificationService(fakeMailSender))
                .clockHolder(new TestClockHolder(1678530673958L))
                .userRepository(fakeUserRepository)
                .build();
        fakeUserRepository.save(User.builder()
                        .id(1L)
                        .email("wjdrltjr5@naver.com")
                        .nickname("wjdrltjr")
                        .address("Busan")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                        .lastLoginAt(0L)
                .build());

        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("'wjdrltjr6@naver.com'")
                .nickname("'wjdrltjr2'")
                .address("'Busan'")
                .status(UserStatus.PENDING)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .lastLoginAt(0L)
                .build());

    }
    @Test
    @DisplayName("getByEmail은 Active 상태인 유저를 찾아올 수 있다.")
    void getByEmail() {
        // given
        String email = "wjdrltjr5@naver.com";
        // when
        User result = userServiceImpl.getByEmail(email);
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
        assertThatThrownBy(() -> userServiceImpl.getByEmail(email)).isInstanceOf(ResourceNotFoundException.class);

    }

    @Test
    @DisplayName("getById로 Active 사용자 찾아오기")
    void getByid() {
        // given

        // when
        User result = userServiceImpl.getById(1);
        // then
        assertThat(result.getNickname()).isEqualTo("wjdrltjr");
    }
    @Test
    @DisplayName("getById로 Pending사용자는 찾을 수 없다.")
    void getByid_Pending() {
        // given
        // when
        // then
        assertThatThrownBy(() -> userServiceImpl.getById(2)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("유저 생성")
    void create() {
        // given
        UserCreate dto = UserCreate.builder().email("wjdrltjr2@naver.com")
                .address("alrnr")
                .nickname("wjdrltjr2")
                .build();
        // when
        User result = userServiceImpl.create(dto);
        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(result.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
    }
    
    @Test
    @DisplayName("User 수정하기")
    void userUpdateDto_를_이용하여_유저를_수정할_수_있다() {
        // given
        UserUpdate dto = UserUpdate.builder().address("Seoul")
                .nickname("awdawd").build();
        // when
        User result = userServiceImpl.update(1,dto);
        // then
        User userEntity = userServiceImpl.getById(1);
        assertThat(userEntity.getNickname()).isEqualTo(dto.getNickname());
        assertThat(userEntity.getAddress()).isEqualTo(dto.getAddress());
    }

    @Test
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
        // given
        // when
        userServiceImpl.login(1);
        // then
        User userEntity = userServiceImpl.getById(1);
        assertThat(userEntity.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
        // given
        // when
        userServiceImpl.verifyEmail(2,"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        // then
        User userEntity = userServiceImpl.getById(2);
        assertThat(userEntity.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> userServiceImpl.verifyEmail(2,"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaccaa"))
                .isInstanceOf(CertificationCodeNotMatchedException.class);
    }
}