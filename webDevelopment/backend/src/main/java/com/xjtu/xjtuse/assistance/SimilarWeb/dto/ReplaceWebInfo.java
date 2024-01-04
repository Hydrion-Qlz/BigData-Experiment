package com.xjtu.xjtuse.assistance.SimilarWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplaceWebInfo {
    private String url;
    private String title;
    private double width;
    private double height;
}
