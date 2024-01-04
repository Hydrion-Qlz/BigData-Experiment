package com.xjtu.xjtuse.assistance.user.impl;


import com.xjtu.xjtuse.assistance.user.UserService;
import com.xjtu.xjtuse.assistance.user.dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;
}
