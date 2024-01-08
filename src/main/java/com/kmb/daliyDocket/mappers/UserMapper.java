package com.kmb.daliyDocket.mappers;

import com.kmb.daliyDocket.entities.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    UserEntity selectUserByEmail(@Param("email") String email);

    UserEntity selectUserByNickName(@Param("nickname") String nickname);

    int insertUser(UserEntity user);

}
