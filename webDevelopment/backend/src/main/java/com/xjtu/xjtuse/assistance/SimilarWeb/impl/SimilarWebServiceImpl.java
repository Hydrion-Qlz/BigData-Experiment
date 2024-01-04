package com.xjtu.xjtuse.assistance.SimilarWeb.impl;

import com.xjtu.xjtuse.assistance.SimilarWeb.SimilarWebService;
import com.xjtu.xjtuse.assistance.SimilarWeb.dao.SimilarWebDao;
import com.xjtu.xjtuse.assistance.SimilarWeb.dto.NewReplaceReq;
import com.xjtu.xjtuse.assistance.SimilarWeb.dto.ReplaceInfoResult;
import com.xjtu.xjtuse.assistance.SimilarWeb.dto.SearchResult;
import com.xjtu.xjtuse.assistance.common.Constants;
import com.xjtu.xjtuse.assistance.common.Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class SimilarWebServiceImpl implements SimilarWebService {
    @Resource
    private SimilarWebDao similarWebDao;

    @Override
    public SearchResult getReplaceInfo(String url, Integer userId) {
        // todo: 调用py分析文件生成id，新线程完成该功能
        SearchResult searchResult = new SearchResult();
        searchResult.setInputUrl(url);
        searchResult.setSimilarWebInfoList(similarWebDao.getSimilarWebInfo(url));

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
        similarWebDao.addSearchHistory(userId, url, sdf.format(date));

        return searchResult;
    }

    @Override
    public ReplaceInfoResult getReplaceInfo(Integer sourceId, Integer targetId) {
        System.out.println(similarWebDao.judgeProcessedReplace(sourceId, targetId));
        if (similarWebDao.judgeProcessedReplace(sourceId, targetId) == null) {
            // 首先检查是否生成过这两个网页的替换, 如果没有的话需要调用 SegmentTransfer 进行分析
            try {
                String sourceUrl = similarWebDao.getUrlById(sourceId);
                String targetUrl = similarWebDao.getUrlById(targetId);
                ProcessBuilder pb = new ProcessBuilder(Constants.PYTHON_INTERPRETER_LOCATION,
                        "D:\\data\\code\\undergradeProject\\WebPageTransfer\\segmentTransfer.py", sourceUrl, targetUrl);
                Process process = pb.start();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
//            }
//            reader.close();
                System.out.println(process.waitFor());
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        // todo: 分析

        // 读取JSON文件获取替换结果并返回
        ReplaceInfoResult replaceInfoResult;
        try {
            String savePath = similarWebDao.getReplaceFileName(sourceId, targetId);
            replaceInfoResult = Utils.loadJson("D:\\data\\code\\undergradeProject\\WebPageTransfer\\data\\replaceInfo\\" + savePath, ReplaceInfoResult.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return replaceInfoResult;
    }

    @Override
    public String makeNewReplace(NewReplaceReq replaceReq) {
        String sourcePath = getHTMLSavePathById(replaceReq.getSourceId());
        String targetPath = getHTMLSavePathById(replaceReq.getTargetId());
        String saveFolder = similarWebDao.getReplaceFileName(replaceReq.getSourceId(), replaceReq.getTargetId());
        saveFolder = saveFolder.replace(".json", "");

        AtomicReference<String> filePath = new AtomicReference<>("");
        String finalSaveFolder = saveFolder;
        Thread t = new Thread(() -> {
            filePath.set(ReplaceUtil.makeReplace(sourcePath, targetPath, finalSaveFolder, replaceReq.getReplaceReqItemList()));
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(filePath);
    }

    private String getHTMLSavePathById(Integer id) {
        String saveFolder = similarWebDao.getSaveFolderById(id);
        Path path = Paths.get(Constants.BASE_PATH_WIN, Constants.SEGMENT_FOLDER_NAME, saveFolder, "index.html");
        return path.toString();
    }


}
