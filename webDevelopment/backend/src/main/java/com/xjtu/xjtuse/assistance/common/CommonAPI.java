package com.xjtu.xjtuse.assistance.common;

import com.xjtu.xjtuse.assistance.common.dao.CommonDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;


@RestController
@RequestMapping("/api/common")
public class CommonAPI {

    @Resource
    private CommonDao commonDao;

    private static String baseUrl;

    // 获取当前的基础路径,如果是win则是IMG_PATH_WIN,如果是linux则是IMG_PATH_LIN
    public CommonAPI() {
        baseUrl = Utils.getBasePath();
    }

    /**
     * 获取视频内容
     *
     * @param fileName 视频文件名称
     * @param resp     返回体
     */
    @GetMapping("/video")
    public void getVideo(String fileName, HttpServletResponse resp) {
        String path = baseUrl + "/video/";
        outPut(path + fileName, resp);
    }

    /**
     * 获得图片内容
     *
     * @param url  文件名称
     * @param type 图片类型, segment 或 replace
     * @param resp 返回体
     */
    @GetMapping("/img")
    public void getImg(String url, String type, HttpServletResponse resp) {
        String typeFolder = Constants.SEGMENT_FOLDER_NAME;
        String urlSaveFolderName;
        if ("replace".equals(type)) {
            typeFolder = Constants.REPLACE_FOLDER_NAME;
            urlSaveFolderName = url;
        } else {
            urlSaveFolderName = commonDao.getWebSaveFolder(url);
        }

        String path = String.join("/", baseUrl, typeFolder, urlSaveFolderName, "screenshot.png");
        outPut(path, resp);
    }

    /**
     * 获得音频内容
     *
     * @param fileName 文件名称
     * @param resp     返回体
     */
    @GetMapping("/audio")
    public void getAudio(String fileName, HttpServletResponse resp) {
        String path = baseUrl + "/audio/";
        outPut(path + fileName, resp);
    }


    /**
     * 将指定路径的文件发送到浏览器中
     *
     * @param path 文件路径
     * @param resp 返回体
     */
    private void outPut(String path, HttpServletResponse resp) {
        try {
            //读取文件的输入流
            InputStream in = new FileInputStream(path);
            //将文件信息输出到浏览器的输出流
            OutputStream out = resp.getOutputStream();

            byte[] b = new byte[10240];//容量为10240字节的缓存区
            int len;//从输入流读取并放入缓存区的数据量

            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            out.flush();
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
