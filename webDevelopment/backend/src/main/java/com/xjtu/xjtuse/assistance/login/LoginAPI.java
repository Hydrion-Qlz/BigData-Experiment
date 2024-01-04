package com.xjtu.xjtuse.assistance.login;

import com.xjtu.xjtuse.assistance.common.Result;
import com.xjtu.xjtuse.assistance.login.dao.LoginDao;
import com.xjtu.xjtuse.assistance.login.dto.LoginInfo;
import com.xjtu.xjtuse.assistance.login.dto.RegisterInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/login")
public class LoginAPI {

    @Resource
    LoginService loginService;

    @Resource
    LoginDao loginDao;

    @PostMapping("/register")
    public Result register(@RequestBody RegisterInfo registerInfo) {
        if (loginDao.emailExist(registerInfo.getEmail())) {
            return Result.fail(Result.ERR_CODE_BUSINESS, "该邮箱已注册");
        }
        loginService.register(registerInfo);
        return Result.success("注册成功");
    }

    @PostMapping("/login")
    public Result register(@RequestBody LoginInfo loginInfo) {
        if (!loginDao.emailExist(loginInfo.getEmail())) {
            return Result.fail(Result.ERR_CODE_BUSINESS, "该邮箱不存在, 请注册后登录");
        }
        if (loginService.login(loginInfo)) {
            return Result.success("登录成功");
        } else {
            return Result.fail(Result.ERR_CODE_BUSINESS, "账号或密码错误");
        }
    }

    @GetMapping("/sendVerificationCode")
    public Result sendVerificationCode(@RequestParam String email) {
        String code = loginService.sendSimpleMail(email);
        return Result.success("", code);
    }
}
