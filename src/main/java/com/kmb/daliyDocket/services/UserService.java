package com.kmb.daliyDocket.services;

import com.kmb.daliyDocket.entities.RecoverEmailCodeEntity;
import com.kmb.daliyDocket.entities.UserEntity;
import com.kmb.daliyDocket.enums.RegisterResult;
import com.kmb.daliyDocket.enums.SearchEmailResult;
import com.kmb.daliyDocket.enums.SendRecoverPasswordResult;
import com.kmb.daliyDocket.mappers.UserMapper;
import com.kmb.daliyDocket.utils.CryptoUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final JavaMailSender javaMailSender; // 추가
    private final SpringTemplateEngine springTemplateEngine;

    @Autowired
    public UserService(UserMapper userMapper, SpringTemplateEngine springTemplateEngine, JavaMailSender javaMailSender) {
        this.userMapper = userMapper;
        this.springTemplateEngine = springTemplateEngine;
        this.javaMailSender = javaMailSender; // 수정
    }


    public RegisterResult registerResult(UserEntity user) {
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

        if (users == null) {
            return SearchEmailResult.FAILED;
        }

        return SearchEmailResult.SUCCESS;
    } //이메일 찾기 결과

    public String findEmail(String name) {
        UserEntity users = this.userMapper.selectUserByName(name);
        return (users != null) ? users.getEmail() : null;
    } //이메일을 데이터베이스에서 찾아서 돌려주기

    public SendRecoverPasswordResult getEmailCheck(RecoverEmailCodeEntity recoverEmailCode) throws MessagingException {
        UserEntity existingUser = this.userMapper.selectUserByEmail(recoverEmailCode.getEmail());
        //작성하는 이메일를 기준으로 User테이블에 select(검색)한다.

        if (existingUser == null) {
            return SendRecoverPasswordResult.FAILURE;
        }
        //유저 테이블에 없는 이메일 입력시 FAILURE 반환

        recoverEmailCode
                .setCode(RandomStringUtils.randomAlphanumeric(6)) //6자리의 랜덤인증번호
                .setSalt(CryptoUtil.hashSha512(String.format("%s%s%f%f",
                        recoverEmailCode.getCode(),
                        recoverEmailCode.getEmail(),
                        Math.random(),
                        Math.random())))
                // 생성되는 솔트 값은 인증 과정에서 사용되는 값으로, 이메일과 인증번호, 그리고 임의의 난수 값을 조합하여 생성됩니다.
                .setCreatedAt(new Date()) // 현재시간
                .setExpiresAt(DateUtils.addMinutes(recoverEmailCode.getCreatedAt(), 5))
                .setExpired(false); //아직 인증을 거치지않았기에 false값
        // 현재시간을 설정해서 현재시간에서 1시간 제한을 둔다.
        // 재설정 url에 사용자의 개인정보(이메일,salt)를 담는 이유는 이메일과 임시로 생성된 솔트 값을 함께 사용하여 인증 절차를 거치게 되는 과정입니다.
        // url 변수에 이메일 값과 솔트 값이 담기는 이유는 비밀번호 재설정을 위한 URL을 생성하기 위해서입니다.

        Context context = new Context();

        String code = recoverEmailCode.getCode();
        context.setVariable("code", code); //context 변수에 인증을 위한 코드를 담는다.

        MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setSubject("[Daily_Docket 비밀번호 재설정] 이메일 인증코드"); //메일의 제목
        mimeMessageHelper.setFrom("chngdch3@naver.com"); //이메일을 보내는 발신자
        mimeMessageHelper.setTo(recoverEmailCode.getEmail()); //이메일 수신자로 설정할 이메일 주소
        mimeMessageHelper.setText(this.springTemplateEngine.process("/user/SendEmailCode", context), true);
        //위에꺼는 제목 및 발신,수신자고 본문에 들어갈 내용을  /user/SendEmailCode(본문) true는 html이라는 의미이다.
        //css가 안되는 이유는 외부나 내부 스타일시트를 이메일 클라이언트에서 지원하지 않기 때문입니다.
        //이유는 이메일 클라이언트의 보안 및 호환성 제약 사항 때문이다.
        this.javaMailSender.send(mimeMessage);

        return this.userMapper.insertRecoverEmailCode(recoverEmailCode) > 0
                ? SendRecoverPasswordResult.SUCCESS
                : SendRecoverPasswordResult.FAILURE;
    }


}
