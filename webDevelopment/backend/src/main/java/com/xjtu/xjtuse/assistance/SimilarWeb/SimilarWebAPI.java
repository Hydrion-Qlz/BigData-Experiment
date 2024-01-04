package com.xjtu.xjtuse.assistance.SimilarWeb;

import com.xjtu.xjtuse.assistance.SimilarWeb.dao.SimilarWebDao;
import com.xjtu.xjtuse.assistance.SimilarWeb.dto.NewReplaceReq;
import com.xjtu.xjtuse.assistance.SimilarWeb.dto.ReplaceInfoResult;
import com.xjtu.xjtuse.assistance.common.Constants;
import com.xjtu.xjtuse.assistance.common.Result;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/similarWeb")
public class SimilarWebAPI {
    @Resource
    SimilarWebService similarWebService;

    @Resource
    SimilarWebDao similarWebDao;

    /**
     * 检索给定url的相似网页
     *
     * @param url 待查找的URL
     * @return 查找结果
     */
    @GetMapping("getSimilarWebInfo")
    public Result getSimilarWebInfo(@RequestParam String url, @RequestParam String color, @RequestParam String category, @RequestParam Integer userId) {
        // todo: 根据主题颜色和类别分析相似页面
        // todo: 处理完后生成消息
        System.out.println(url);
        System.out.println(color);
        System.out.println(category);
        return Result.success(similarWebService.getReplaceInfo(url, userId));
    }

    /**
     * 获取网页替换信息
     *
     * @param sourceId 源网页id
     * @param targetId 目标网页id
     * @return 网页替换信息 ReplaceInfoResult
     */
    @GetMapping("getReplaceInfo")
    public Result getReplaceInfo(@RequestParam Integer sourceId, @RequestParam Integer targetId) {
        ReplaceInfoResult replaceInfoResult;
        // 另起进程分析网页替换信息，保持网页开启状态，每次发送回来替换信息返回截图回去
        // todo: 目前只会起一个线程，后续根据token起多个线程，放在map中对应
        replaceInfoResult = similarWebService.getReplaceInfo(sourceId, targetId);

        return Result.success(replaceInfoResult);
    }

    @GetMapping("getReplacePreviewImageUrl")
    public Result getReplacePreviewImageUrl(@RequestParam String sourceUrl, @RequestParam String targetUrl) {
        String saveFolder = similarWebDao.getReplacePreviewImageUrl(sourceUrl, targetUrl);
        saveFolder = saveFolder.replace(".json", "");
        return Result.success("成功", saveFolder);
    }

    @PostMapping("makeNewReplace")
    public Result makeNewReplace(@RequestBody NewReplaceReq replaceReq) {
        System.out.println(replaceReq);
        String filePath = similarWebService.makeNewReplace(replaceReq);

        return Result.success(filePath);
    }

    @GetMapping("saveReplaceResult")
    public ResponseEntity<byte[]> saveReplaceResult(@RequestParam Integer sourceId, @RequestParam Integer targetId) {
        String saveFolder = similarWebDao.getReplaceFileName(sourceId, targetId);
        saveFolder = saveFolder.replace(".json", "");

        // 从后端获取文件内容并将其保存在一个变量中
        Path path = Paths.get(Constants.TEMP_DIR, saveFolder, "index.html");
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 设置HTTP响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XHTML_XML);
        headers.setContentDisposition(ContentDisposition.attachment().filename("index.html").build());

        // 创建HTTP响应实体
        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }
}
