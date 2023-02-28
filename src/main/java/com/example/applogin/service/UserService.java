package com.example.applogin.service;

import com.example.applogin.bean.User;
import org.springframework.stereotype.Component;

@Component
public interface UserService {
    User getUserByUserId(String userid);

    String createNewUser(User user);
}
