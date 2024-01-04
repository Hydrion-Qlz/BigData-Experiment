package com.xjtu.xjtuse.assistance.SimilarWeb.dao;

import com.xjtu.xjtuse.assistance.SimilarWeb.SimilarWebInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
//@Mapper
public interface SimilarWebDao {
    String getSaveFolderById(Integer id);

    List<SimilarWebInfo> getSimilarWebInfo(String url);

    String getReplacePreviewImageUrl(String sourceUrl, String targetUrl);

    String getReplaceFileName(Integer sourceId, Integer targetId);

    String getUrlById(Integer id);

    Boolean judgeProcessedReplace(Integer sourceId, Integer targetId);

    void addSearchHistory(Integer userId, String url, String time);
}
