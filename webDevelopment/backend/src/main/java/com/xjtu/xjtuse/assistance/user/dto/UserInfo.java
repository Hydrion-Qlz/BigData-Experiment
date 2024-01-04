package com.xjtu.xjtuse.assistance.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private int id;
    private String username;
    private String email;
    private int age;
    private String gender;
    private String birthday;
    private String hobbies;
    private int state;
}
