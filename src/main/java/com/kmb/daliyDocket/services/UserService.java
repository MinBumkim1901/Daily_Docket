package com.kmb.daliyDocket.services;

import com.kmb.daliyDocket.Entity.UserEntity;
import com.kmb.daliyDocket.enums.RegisterResult;
import com.kmb.daliyDocket.mappers.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public RegisterResult registerResult(UserEntity user) {
        System.out.println(user.getEmail());
        if (userMapper.selectUserByEmail(user.getEmail()) != null) {
            return RegisterResult.DUPLICATE_EMAIL;
        } //null이 아닐경우 가입한 이메일이 있기때문에 실패!

        if (userMapper.selectUserByNickName(user.getNickname()) != null) {
            return RegisterResult.DUPLICATE_NICKNAME;
        } //null이 아닐 경우 가입한 닉네임이 있기 때문에 실패!

        return userMapper.insertUser(user) > 0
                ? RegisterResult.SUCCESS
                : RegisterResult.FAILURE;
        //삼항식을 사용해 성공시 Insert(데이터 삽입) 실패시 회원가입 메서드 종료
    }
}
