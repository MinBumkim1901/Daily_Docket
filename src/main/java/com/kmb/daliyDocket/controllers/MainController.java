package com.kmb.daliyDocket.controllers;

import com.kmb.daliyDocket.entities.UserEntity;
import com.kmb.daliyDocket.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/")
public class MainController {

    private final UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView getMain(){
        ModelAndView modelAndView = new ModelAndView("home/main");
        return modelAndView;
    }
}
