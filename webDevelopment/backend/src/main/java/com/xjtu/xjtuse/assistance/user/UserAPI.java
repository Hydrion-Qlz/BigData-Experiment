package com.xjtu.xjtuse.assistance.user;

import com.xjtu.xjtuse.assistance.common.Result;
import com.xjtu.xjtuse.assistance.user.dao.UserDao;
import com.xjtu.xjtuse.assistance.user.dto.Message;
import com.xjtu.xjtuse.assistance.user.dto.PasswordInfo;
import com.xjtu.xjtuse.assistance.user.dto.SearchHistoryItem;
import com.xjtu.xjtuse.assistance.user.dto.UserInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserAPI {

    @Resource
    UserDao userDao;

    @Resource
    UserService userService;

    @GetMapping("/getUserInfo")
    public Result getUserInfo(@RequestParam String email) {
        UserInfo userInfo = userDao.getUserInfo(email);
        if (userInfo.getId() == 0) {
            return Result.fail(Result.ERR_CODE_BUSINESS, "未找到当前用户信息");
        }
        return Result.success(userInfo);
    }

    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody UserInfo userInfo) {
        userDao.updateUserInfo(userInfo);
        return Result.success();
    }

    @GetMapping("/getMessageList")
    public Result getMessageList(@RequestParam Integer userId) {
        List<Message> messageList = userDao.getMessageList(userId);
        return Result.success(messageList);
    }

    @PostMapping("/changePwd")
    public Result changePwd(@RequestBody PasswordInfo passwordInfo) {
        userDao.changePwd(passwordInfo);
        return Result.success();
    }

    @GetMapping("/getSearchHistory")
    public Result getSearchHistory(@RequestParam Integer userId) {
        List<SearchHistoryItem> searchHistoryItems = userDao.getSearchHistory(userId);
        return Result.success(searchHistoryItems);
    }
}
