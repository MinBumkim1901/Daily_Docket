package com.kmb.daliyDocket.services;

import com.kmb.daliyDocket.entities.UserEntity;
import com.kmb.daliyDocket.enums.RegisterResult;
import com.kmb.daliyDocket.mappers.UserMapper;
import com.kmb.daliyDocket.utils.CryptoUtil;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public RegisterResult registerResult(UserEntity user){
        if (userMapper.selectUserByEmail(user.getEmail()) != null) {
            return RegisterResult.DUPLICATE_EMAIL;
        } //null이 아닐경우 가입한 이메일이 있기때문에 실패!

        if (userMapper.selectUserByNickName(user.getNickname()) != null) {
            return RegisterResult.DUPLICATE_NICKNAME;
        } //null이 아닐 경우 가입한 닉네임이 있기 때문에 실패!

        user.setPassword(CryptoUtil.hashSha512(user.getPassword()));
        user.setAdmin(false);

        return userMapper.insertUser(user) > 0
                ? RegisterResult.SUCCESS
                : RegisterResult.FAILURE;
        //삼항식을 사용해 성공시 Insert(데이터 삽입) 실패시 회원가입 메서드 종료
    }

    public boolean login(UserEntity user) {
        if (user.getEmail() == null ||
                user.getPassword() == null ||
                !user.getEmail().matches("^(?=.{10,50}$)([\\da-zA-Z\\-_.]{5,25})@([\\da-z][\\da-z\\-]*[\\da-z]\\.)?([\\da-z][\\da-z\\-]*[\\da-z])\\.([a-z]{2,15})(\\.[a-z]{2})?$") ||
                !user.getPassword().matches("^([\\da-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]};:'\",<.>/?]{8,50})$")) {
            return false;
        } //이메일이 없거나 비밀번호 없거나 이메일이나 비번 규칙에 맞지 않을때 실패

        UserEntity existingUser = this.userMapper.selectUserByEmail(user.getEmail());

        user.setPassword(CryptoUtil.hashSha512(user.getPassword())); // test1234 -> hashed
        if (!user.getPassword().equals(existingUser.getPassword())) {
            return false;
        }

        user.setNickname(existingUser.getNickname());
        return true;
    }

    public UserEntity getUser(HttpSession session) {
        UserEntity user = (UserEntity) session.getAttribute("user");
        return this.userMapper.selectUserByEmail(user.getEmail());
    }
}
