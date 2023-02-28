package com.example.applogin.service.impl;

import com.example.applogin.bean.User;
import com.example.applogin.dao.UserDao;
import com.example.applogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User getUserByUserId(String userid) {
        User user  = userDao.getUserById(userid);
        return user;
    }

    @Override
    public String createNewUser(User user) {
        return userDao.createNewUser(user);
    }
}
