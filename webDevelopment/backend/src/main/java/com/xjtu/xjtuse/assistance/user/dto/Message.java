package com.xjtu.xjtuse.assistance.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private int id;
    private int userId;
    private String message;
    private String time;
    private int state; //0未读，1已读
}
