package com.example.demo.post.controller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostResponseTest {

    @Test
    @DisplayName("Post로 응답을 생성할 수 있다.")
    void from() {
        // given
        Post post = Post.builder()
                .content("helloworld")
                .writer(User.builder()
                        .id(1l)
                        .email("wjdrltjr5@naver.com")
                        .address("Busan")
                        .nickname("wjdrltjr")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaaaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaab")
                        .build())
                .build();
        // when

        PostResponse postResponse = PostResponse.from(post);
        // then

        assertThat(postResponse.getContent()).isEqualTo("helloworld");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("wjdrltjr5@naver.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("wjdrltjr");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
    
}