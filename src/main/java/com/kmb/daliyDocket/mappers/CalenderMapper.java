package com.kmb.daliyDocket.mappers;

import com.kmb.daliyDocket.entities.CalenderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CalenderMapper {
    int insertSchedule(CalenderEntity calenderEntity);
    //스케줄 삽입 매퍼

    CalenderEntity[] selectSchedule(@Param(value = "email")String email);
    //스케줄 조회 매퍼

}
