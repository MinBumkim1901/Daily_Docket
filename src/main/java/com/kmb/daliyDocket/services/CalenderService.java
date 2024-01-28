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
}
