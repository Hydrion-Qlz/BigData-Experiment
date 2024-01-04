package com.xjtu.xjtuse.assistance.SimilarWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewReplaceReqItem {
    private SegmentInfo source;
    private SegmentInfo target;
}
