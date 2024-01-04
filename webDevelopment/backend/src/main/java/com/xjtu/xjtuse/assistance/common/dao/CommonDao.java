package com.xjtu.xjtuse.assistance.common.dao;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonDao {
    @Select("select save_path as savePath from webinfo where url = #{url}")
    String getWebSaveFolder(String url);
}
