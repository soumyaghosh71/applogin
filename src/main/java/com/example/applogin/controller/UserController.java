package com.example.applogin.controller;

import com.example.applogin.bean.User;
import com.example.applogin.constants.Constants;
import com.example.applogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String successLogin(ModelMap model, @RequestParam String userId, @RequestParam String password) {
        User user = userService.getUserByUserId(userId);
        if (user == null) {
            model.put("errorMsg", "User not found !");
            return "login";
        }
        if(user.getPassword().equals(password)) {
            model.put("user", userId);
            return "feed";
        }
        model.put("errorMsg", "Please provide the correct password !");
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, ModelMap model) {
        String status = userService.createNewUser(user);

        if (status.equals(Constants.REGISTER_FAILED)) {
            model.put("errorMsg", "Some issue occurred with registration");
            return "register";
        } else if (status.equals(Constants.REGISTER_FAILED_DUPLICATE_USERID)) {
            model.put("errorMsg", Constants.REGISTER_FAILED_DUPLICATE_USERID);
            return "register";
        }

        model.put("successMsg", "User created successfully!!");
        return "login";
    }
}
