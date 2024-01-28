package com.kmb.daliyDocket.controllers;

import com.kmb.daliyDocket.entities.CalenderEntity;
import com.kmb.daliyDocket.services.CalenderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/")
public class CalenderController {

    private final CalenderService calenderService;

    public CalenderController(CalenderService calenderService) {
        this.calenderService = calenderService;
    } //종속성

    @RequestMapping(value = "/calender", method = RequestMethod.GET)
    public ModelAndView getCalender() {
        ModelAndView modelAndView = new ModelAndView("calender/calender");
        // 뷰 페이지의 이름을 "calendar"로 설정
        String viewpage = "calendar";

//        // CalendarService를 통해 일정 목록을 가져오기 위한 리스트 선언
//        List<Calendar> calendar = null;
//
//        try {
//            // CalendarService를 사용하여 일정 목록을 가져오기 시도
//            calendar = calendarService.getCalendar();
//
//            // 가져온 일정 목록을 HttpServletRequest에 "calendarList"라는 속성으로 설정
//            request.setAttribute("calendarList", calendar);
//        } catch (Exception e) {
//            // 예외 발생 시 예외 정보 출력
//            e.printStackTrace();
//        }
//
//        // ModelAndView의 뷰 이름을 설정
//        mv.setViewName(viewpage);
//
//        // ModelAndView 반환
        return modelAndView;
    }

    @RequestMapping(value = "/calender", method = RequestMethod.POST)
    @ResponseBody //Http요청을 본문에 담기위한 어노테이션
    public String postCalender(CalenderEntity calender, HttpSession session,
                               @RequestParam(value = "start") String startDate,
                               @RequestParam(value = "end") String endDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date start = sdf.parse(startDate);
        Date end = sdf.parse(endDate);

        calender.setStartDate(start);
        calender.setEndDate(end);

        boolean result = this.calenderService.postCalender(calender, session);
        return String.valueOf(result);
    } //일정추가 메서드


}
