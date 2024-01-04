package com.xjtu.xjtuse.assistance.user.dao;

import com.xjtu.xjtuse.assistance.user.dto.Message;
import com.xjtu.xjtuse.assistance.user.dto.PasswordInfo;
import com.xjtu.xjtuse.assistance.user.dto.SearchHistoryItem;
import com.xjtu.xjtuse.assistance.user.dto.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    UserInfo getUserInfo(String email);

    void updateUserInfo(@Param("userInfo") UserInfo userInfo);

    List<Message> getMessageList(Integer userId);

    void changePwd(PasswordInfo passwordInfo);

    List<SearchHistoryItem> getSearchHistory(Integer userId);
}
