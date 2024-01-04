package com.xjtu.xjtuse.assistance.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebPageInfo {
    private Integer id;
    private Integer userId;
    private String username;
    private String email;
    private String url;
    private String savePath;
    private String primaryClass;
    private String secondClass;
    private Integer state;
}
