package com.xjtu.xjtuse.assistance.admin;

import com.xjtu.xjtuse.assistance.admin.dao.AdminDao;
import com.xjtu.xjtuse.assistance.admin.dto.UserState;
import com.xjtu.xjtuse.assistance.admin.dto.WebPageInfo;
import com.xjtu.xjtuse.assistance.admin.dto.WebPageState;
import com.xjtu.xjtuse.assistance.common.Result;
import com.xjtu.xjtuse.assistance.user.dto.UserInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminAPI {
    @Resource
    AdminDao adminDao;

    @Resource
    AdminService adminService;

    @GetMapping("getAllUserInfo")
    public Result getAllUser() {
        List<UserInfo> userInfoList = adminDao.getAllUser();
        return Result.success(userInfoList);
    }

    @PostMapping("setUserState")
    public Result setUserState(@RequestBody UserState userState) {
        adminDao.setUserState(userState.getUserId(), userState.getState());
        return Result.success();
    }

    @GetMapping("getVerifyPageList")
    public Result getVerifyPageList() {
        List<WebPageInfo> userInfoList = adminDao.getVerifyPageList();
        return Result.success(userInfoList);
    }

    @PostMapping("setWebPageState")
    public Result setWebPageState(@RequestBody WebPageState webPageState) {
        adminDao.setWebPageState(webPageState.getId(), webPageState.getState());
        return Result.success();
    }
}
