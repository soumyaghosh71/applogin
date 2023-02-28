package com.example.applogin.dao;

import com.example.applogin.bean.User;
import org.springframework.stereotype.Component;

@Component
public interface UserDao {
    User getUserById(String userid);

    String createNewUser(User user);
}
