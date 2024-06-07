package com.example.demo.post.domain;

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

    public static Post from(User writer, PostCreate postCreate) {
        return Post.builder()
                .content(postCreate.getContent())
                .writer(writer)
                .createdAt(Clock.systemUTC().millis())
                .modifiedAt(Clock.systemUTC().millis())
                .build();
    }

    public Post update(PostUpdate postUpdate) {
        return Post.builder()
                        .content(postUpdate.getContent())
                                .modifiedAt(Clock.systemUTC().millis())
                                        .writer(writer)
                                                .id(id)
                                                        .createdAt(createdAt)
                .build();
    }
}
