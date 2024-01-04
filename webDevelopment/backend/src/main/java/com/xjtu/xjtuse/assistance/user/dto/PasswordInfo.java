package com.xjtu.xjtuse.assistance.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordInfo {
    private String email;
    private String password;
}
