package com.pizzeria.controller;

import com.pizzeria.entity.User;
import com.pizzeria.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController (UserService userService){
        this.userService=userService;
    }

    @GetMapping("/users")
    public ModelAndView users() {
        List<User> users = userService.findAll();
        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", users);
        return modelAndView;
    }

    @GetMapping("/create") public ModelAndView showCreateUserForm() {
        ModelAndView modelAndView = new ModelAndView("createUser");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/create") public User createUser(@ModelAttribute User user) {
        return userService.saveUser(user);
    }


}
