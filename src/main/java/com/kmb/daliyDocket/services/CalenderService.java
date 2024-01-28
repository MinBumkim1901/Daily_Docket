package com.kmb.daliyDocket.services;

import com.kmb.daliyDocket.entities.CalenderEntity;
import com.kmb.daliyDocket.entities.UserEntity;
import com.kmb.daliyDocket.mappers.CalenderMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class CalenderService {
    private final CalenderMapper calenderMapper;

    public CalenderService(CalenderMapper calenderMapper) {
        this.calenderMapper = calenderMapper;
    }

    public boolean postCalender(CalenderEntity calender, HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        //로그인한 유저의 세션 정보를 받아온다.

        if(user == null){
            return false;
        }

        calender.setEmail(user.getEmail());
        return calenderMapper.insertSchedule(calender) > 0;
    }

    public CalenderEntity[] selectSchedule(HttpSession session){

        UserEntity user = (UserEntity) session.getAttribute("user");
        CalenderEntity[] schedule = calenderMapper.selectSchedule(user.getEmail());

        if (schedule == null) {
            // 스케줄이 없는 경우 빈 배열을 반환
            return null;
        } else {
            // 스케줄이 있는 경우 해당 스케줄 배열 반환
            return schedule;
        }
    }
}
