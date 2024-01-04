package com.xjtu.xjtuse.assistance.login.impl;

import com.xjtu.xjtuse.assistance.login.LoginService;
import com.xjtu.xjtuse.assistance.login.dao.LoginDao;
import com.xjtu.xjtuse.assistance.login.dto.LoginInfo;
import com.xjtu.xjtuse.assistance.login.dto.RegisterInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Random;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {
    /**
     * Spring Boot 提供了一个发送邮件的简单抽象，使用的是下面这个接口，这里直接注入即可使用
     */
    @Resource
    private JavaMailSender mailSender;

    @Resource
    private LoginDao loginDao;

    /**
     * 配置文件中我的qq邮箱
     */
    @Value("${spring.mail.from}")
    private String from;

    /**
     * 简单文本邮件
     *
     * @param to 收件人
     */
    public String sendSimpleMail(String to) {
        String subject = "Assistance验证码";
        String code = generateNumberCode(6);
        String content = "验证码为： " + code;
        // todo: 另起线程完成工作
        //
        //        //创建SimpleMailMessage对象
        //        SimpleMailMessage message = new SimpleMailMessage();
        //        //邮件发送人
        //        message.setFrom(from);
        //        //邮件接收人
        //        message.setTo(to);
        //        //邮件主题
        //        message.setSubject(subject);
        //        //邮件内容
        //        message.setText(content);
        //        //发送邮件
        //        mailSender.send(message);

        return code;
    }

    @Override
    public void register(RegisterInfo registerInfo) {
        loginDao.register(registerInfo.getEmail(), registerInfo.getPassword());
    }

    @Override
    public Boolean login(LoginInfo loginInfo) {
        String pwd = loginDao.getPwd(loginInfo.getEmail());
        return pwd.equals(loginInfo.getPassword());
    }

    public static String generateNumberCode(int len) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
