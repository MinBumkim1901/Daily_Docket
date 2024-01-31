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
    public ModelAndView getMain(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("home/main");
        // 세션에서 로그인한 사용자 정보를 가져옵니다.
        UserEntity user = (UserEntity) session.getAttribute("user");

        // 만약 사용자 정보가 있다면 모델에 추가합니다.
        if (user != null) {
            modelAndView.addObject("user", user);
        }

        return modelAndView;
    }
}
