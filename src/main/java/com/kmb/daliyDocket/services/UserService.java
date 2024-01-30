package com.kmb.daliyDocket.services;

import com.kmb.daliyDocket.entities.UserEntity;
import com.kmb.daliyDocket.enums.RegisterResult;
import com.kmb.daliyDocket.enums.SearchEmailResult;
import com.kmb.daliyDocket.enums.SendRecoverPasswordResult;
import com.kmb.daliyDocket.mappers.UserMapper;
import com.kmb.daliyDocket.utils.CryptoUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

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
    //회원가입 결과

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
    //로그인 결과

    public SearchEmailResult emailResult(String name, Date birthday) {
        UserEntity users = this.userMapper.selectUserByName(name);

        if(users == null){
            return SearchEmailResult.FAILED;
        }

        return SearchEmailResult.SUCCESS;
    } //이메일 찾기 결과

    public String findEmail(String name) {
        UserEntity users = this.userMapper.selectUserByName(name);
        return (users != null) ? users.getEmail() : null;
    } //이메일을 데이터베이스에서 찾아서 돌려주기

    public SendRecoverPasswordResult getEmailCheck(SendRecoverPasswordResult sendRecoverPasswordResult) {
//        UserEntity existingUser = this.recoverMapper.selectUserByEmailContact(recoverEmailContactCode.getEmail(), recoverEmailContactCode.getContact());
//        //작성하는 이메일과 전화번호를 기준으로 User테이블에 select(검색)한다.
//        if (existingUser == null) {
//            return SendRecoverContactCodeResult.FAILURE;
//        }
//        //유저 테이블에 없는 이메일이나 전화번호 입력시 FAILURE 반환
//
//        String code = RandomStringUtils.randomNumeric(6); //랜덤 숫자 6자리 (인증번호)
//        String salt = CryptoUtil.hashSha512(String.format("%s%s%f%f", //비밀번호 암호화
//                recoverEmailContactCode.getCode(),
//                code,
//                Math.random(),
//                Math.random()));
//        Date createdAt = new Date(); //시간제한을 걸기위한 Date값
//        Date expiresAt = DateUtils.addMinutes(createdAt, 5); //시간제한 5분
//        recoverEmailContactCode.setCode(code).setSalt(salt).setCreatedAt(createdAt).setExpiresAt(expiresAt).setExpired(false);
//
//        NCloudUtil.sendSms(recoverEmailContactCode.getContact(), String.format("[UFO 계정 복구] 비밀번호 변경 인증번호[%s]를 입력해주세요", recoverEmailContactCode.getCode()));
//
//        return this.recoverMapper.insertRecoverEmailContactCode(recoverEmailContactCode) > 0
//                ? SendRecoverContactCodeResult.SUCCESS
//                : SendRecoverContactCodeResult.FAILURE;
        return null;
    }


}
