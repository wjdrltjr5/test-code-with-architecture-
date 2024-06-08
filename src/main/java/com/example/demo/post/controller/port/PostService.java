package com.example.demo.post.controller.port;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.domain.User;

public interface PostService {
    public Post getById(long id) ;

    public Post create(PostCreate postCreate) ;

    public Post update(long id, PostUpdate postUpdate) ;
}
