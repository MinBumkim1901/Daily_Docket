package com.kmb.daliyDocket.controllers;

import com.kmb.daliyDocket.entities.CalenderEntity;
import com.kmb.daliyDocket.services.CalenderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/")
public class CalenderController {

    private final CalenderService calenderService;

    public CalenderController(CalenderService calenderService) {
        int a = 5;
        this.calenderService = calenderService;
    }

    @RequestMapping(value = "/calender", method = RequestMethod.GET)
    public ModelAndView getCalender() {
        ModelAndView modelAndView = new ModelAndView("calender/calender");
        return modelAndView;
    }

    @RequestMapping(value = "/calender", method = RequestMethod.POST)
    @ResponseBody //Http요청을 본문에 담기위한 어노테이션
    public String postCalender(CalenderEntity calender, HttpSession session) {
        boolean result = this.calenderService.postCalender(calender, session);
        return String.valueOf(result);
    }
}
