package com.xjtu.xjtuse.assistance.SimilarWeb;

import com.xjtu.xjtuse.assistance.SimilarWeb.dto.NewReplaceReq;
import com.xjtu.xjtuse.assistance.SimilarWeb.dto.ReplaceInfoResult;
import com.xjtu.xjtuse.assistance.SimilarWeb.dto.SearchResult;

public interface SimilarWebService {
    SearchResult getReplaceInfo(String url, Integer userId);

    ReplaceInfoResult getReplaceInfo(Integer sourceId, Integer targetId);

    String makeNewReplace(NewReplaceReq replaceReq);
}
