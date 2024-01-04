package com.xjtu.xjtuse.assistance.SimilarWeb.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SegmentInfo {
    private Integer segmentId;
    private String segmentCssSelector;
    private double similarity;
    private Bound bound;
    private List<String> records;
}
