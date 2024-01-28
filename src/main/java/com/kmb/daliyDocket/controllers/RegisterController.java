package com.kmb.daliyDocket.controllers;

import com.kmb.daliyDocket.entities.UserEntity;
import com.kmb.daliyDocket.enums.RegisterResult;
import com.kmb.daliyDocket.services.UserService;
import org.json.JSONObject;
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
public class RegisterController {
    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLogin() {
        ModelAndView modelAndView = new ModelAndView("login/login");
        return modelAndView;
    }

    @RequestMapping(value = "/findAccount", method = RequestMethod.GET)
    public ModelAndView getFindAccount() {
        ModelAndView modelAndView = new ModelAndView("login/findAccount");
        return modelAndView;
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public String postLogin(HttpSession session,UserEntity user){
        boolean result = this.userService.login(user);

        if(result){
            session.setAttribute("user",user);
        } //로그인 성공시 user의 세션정보 유지

        return String.valueOf(result);
    }

    @RequestMapping(value="logout", method = RequestMethod.GET)
    public ModelAndView getLogout(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        session.setAttribute("user",null);
        return modelAndView;
    } //로그아웃 클릭시 세션값을 null로 설정해 로그인 풀리게 하는 GET메서드

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody //HTTP 본문요청 되게해준다
    public String getRegister(UserEntity user,
                              @RequestParam(value = "birthStr")String birthStr) throws ParseException{
        // sdf를 이용해서 넘어오는 date타입을 매칭 시켜준다 년-월-일, 하지않을 경우 미스매치가 일어남
        // js에서도 birStr로 넘기고 있으며 받아올때도 birthStr ->데이트 값을 문자로 변환 시킬것이기 때문에 이렇게 이름을 지음
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = sdf.parse(birthStr);
        user.setBirth(birth);

        RegisterResult result = userService.registerResult(user);
        JSONObject responseObject = new JSONObject() {{
            put("result", result.name().toLowerCase());
        }};
        return String.valueOf(responseObject);
    }
}
