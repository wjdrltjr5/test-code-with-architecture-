package com.example.demo.post.domain;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    @DisplayName("PostCreate 로 게시글을 작성할 수 있다.")
    void from() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("hello world")
                .build();
        User writer =  User.builder()
                .id(1l)
                .email("wjdrltjr5@naver.com")
                .address("Busan")
                .nickname("wjdrltjr")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaab")
                .build();

        // when
        Post post = Post.from(writer, postCreate);
        // then
        assertThat(post.getContent()).isEqualTo("hello world");
        assertThat(post.getWriter().getEmail()).isEqualTo("wjdrltjr5@naver.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("wjdrltjr");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaab");
    }
    @Test
    @DisplayName("PostUpdate 로 게시글을 수정할 수 있다.")
    void update() {
        // given

        // when

        // then
    }

}