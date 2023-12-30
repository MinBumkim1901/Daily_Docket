package com.kmb.daliyDocket.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/")
public class accountController {
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView getMain(){
        ModelAndView modelAndView = new ModelAndView("login/login");
        return modelAndView;
    }
}
