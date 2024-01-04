package com.xjtu.xjtuse.assistance.SimilarWeb.dto;

import com.xjtu.xjtuse.assistance.SimilarWeb.SimilarWebInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {
    private String inputUrl;
    private List<SimilarWebInfo> similarWebInfoList;
}
