package com.xjtu.xjtuse.assistance.admin.dao;

import com.xjtu.xjtuse.assistance.admin.dto.WebPageInfo;
import com.xjtu.xjtuse.assistance.user.dto.UserInfo;

import java.util.List;

public interface AdminDao {
    List<UserInfo> getAllUser();

    void setUserState(Integer userId, Integer state);

    List<WebPageInfo> getVerifyPageList();

    void setWebPageState(Integer id, Integer state);
}
