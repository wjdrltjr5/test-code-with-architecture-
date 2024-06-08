package com.example.demo.post.domain;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.Clock;

@Builder
@Getter
public class Post {
    private final Long id;
    private final String content;
    private final Long createdAt;
    private final Long modifiedAt;
    private final User writer;

    public static Post from(User writer, PostCreate postCreate, ClockHolder clockHolder) {
        return Post.builder()
                .content(postCreate.getContent())
                .writer(writer)
                .createdAt(clockHolder.millis())
                .modifiedAt(clockHolder.millis())
                .build();
    }

    public Post update(PostUpdate postUpdate, ClockHolder clockHolder) {
        return Post.builder()
                        .content(postUpdate.getContent())
                                .modifiedAt(clockHolder.millis())
                                        .writer(writer)
                                                .id(id)
                                                        .createdAt(createdAt)
                .build();
    }
}
