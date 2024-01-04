package com.xjtu.xjtuse.assistance.SimilarWeb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplaceInfoResult {
    private String saveFolder;
    private ReplaceWebInfo sourceInfo;
    private ReplaceWebInfo targetInfo;
    private List<ReplaceInfo> replaceInfo;
}
