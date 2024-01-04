package com.xjtu.xjtuse.assistance.SimilarWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplaceInfo {
    private SegmentInfo source;
    private List<SegmentInfo> target;
}
