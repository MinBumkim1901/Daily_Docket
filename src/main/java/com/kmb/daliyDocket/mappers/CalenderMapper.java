package com.kmb.daliyDocket.mappers;

import com.kmb.daliyDocket.entities.CalenderEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CalenderMapper {
    int insertSchedule(CalenderEntity calenderEntity);
    //스케줄 삽입 매퍼

}
