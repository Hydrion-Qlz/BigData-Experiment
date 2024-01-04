package com.xjtu.xjtuse.assistance.login;

import com.xjtu.xjtuse.assistance.login.dto.LoginInfo;
import com.xjtu.xjtuse.assistance.login.dto.RegisterInfo;

public interface LoginService {
    public String sendSimpleMail(String to);

    void register(RegisterInfo registerInfo);

    Boolean login(LoginInfo loginInfo);
}
