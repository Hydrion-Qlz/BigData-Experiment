package com.xjtu.xjtuse.assistance.login.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface LoginDao {
    boolean emailExist(String email);

    void register(String email, String password);

    String getPwd(String email);
}
