package com.xjtu.xjtuse.assistance.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchHistoryItem {
    private Integer id;
    private Integer userId;
    private String webUrl;
    private String startTime;
    private Integer percentage;
}
