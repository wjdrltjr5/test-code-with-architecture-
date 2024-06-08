package com.example.demo.user.controller.port;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdate;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    User getByEmail(String email) ;
    User getById(long id) ;
    User create(UserCreate userCreate) ;
    User update(long id, UserUpdate userUpdate);
    void login(long id);
    void verifyEmail(long id, String certificationCode) ;

}
