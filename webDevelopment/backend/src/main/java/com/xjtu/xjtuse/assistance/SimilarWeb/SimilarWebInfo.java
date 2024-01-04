package com.xjtu.xjtuse.assistance.SimilarWeb;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimilarWebInfo {
    private Integer id;
    private String sourceUrl;
    private Integer sourceId;
    private String targetUrl;
    private Integer targetId;
    private Double similarity;
    private String algorithm;
    private String targetFolderName;
}
