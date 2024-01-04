package com.xjtu.xjtuse.assistance.common;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xjtu.xjtuse.assistance.common.page.PageParam;
import com.xjtu.xjtuse.assistance.common.page.QueryAction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.springframework.format.annotation.DateTimeFormat;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class Utils {

    /**
     * 根据路径名判断一个文件夹是否存在
     */
    public static boolean dirExists(String dir) {
        File file = new File(dir);
        return file.exists() && file.isDirectory();
    }

    /**
     * 创建一个文件夹
     */
    public static boolean createDir(String dir) {
        File file = new File(dir);
        return file.mkdirs();
    }

    /**
     * 分页器
     */
    public static <T> Map<String, Object> getPage(PageParam pageParam, QueryAction<T> queryAction) {
        PageHelper.startPage(pageParam);

        List<T> list = queryAction.execute();

        PageInfo<T> pageInfo = new PageInfo<>(list);

        Map<String, Object> page = new HashMap<>();

        page.put("current", pageInfo.getPageNum());
        page.put("pageSize", pageInfo.getPageSize());//每页最大记录数
        page.put("total", pageInfo.getTotal());//总记录数
        page.put("pages", pageInfo.getPages());//总页数
        page.put("size", pageInfo.getSize());//当前页实际记录数
        page.put("list", pageInfo.getList());//当前页的数据记录
        page.put("first", 1);
        page.put("pre", pageInfo.getPrePage());
        page.put("next", pageInfo.getNextPage());
        page.put("last", pageInfo.getPages());

        return page;
    }

    /**
     * 将一个Java对象转化为json串，并向浏览器（客户端输出）
     *
     * @param resp 相应对象
     * @param obj  需要转化为json串的java对象
     */
    public static void outJson(HttpServletResponse resp, Object obj) throws IOException {
        resp.setContentType("application/json;charset=UTF-8");

        PrintWriter out = resp.getWriter();//获取向客户端发送字符信息流对象
        // 将list集合对象转化为json格式字符串
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(obj);
        out.print(jsonString);

        out.flush();
        out.close();
    }

    /**
     * 获取并返回当前日期，返回一个字符串
     * 如：20210607
     */
    public static String getDateString() {
        Calendar calender = Calendar.getInstance();
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH) + 1;
        String mon = month < 10 ? ("0" + month) : ("" + month);
        int day = calender.get(Calendar.DAY_OF_MONTH);
        String d = day < 10 ? ("0" + day) : ("" + day);
        return Integer.toString(year) + mon + d;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public static String getNowString() {
        Calendar cal = Calendar.getInstance();
        try {
            // 设置最后修改时间
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(dateFormat.format(cal.getTime())).toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public static Date getNow() {
        Calendar cal = Calendar.getInstance();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(dateFormat.format(cal.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param min 下限
     * @param max 上限（不含）
     * @return 一个大于等于min，小于max的正整数
     */
    public int generateRandomInt(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    public List<Integer> generateRandomIntList(int min, int max, int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            list.add(generateRandomInt(min, max));
        }

        return list;
    }

    /***
     * 利用Apache的工具类实现SHA-256加密
     * @return str 加密后的报文
     */
    public static String getSHA256Str(String str) {
        MessageDigest messageDigest;
        String encodeSir = str;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hash = messageDigest.digest(str.getBytes(StandardCharsets.UTF_8));
            encodeSir = Hex.encodeHexString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encodeSir;
    }

    /**
     * 通过该方法将密码加密（实际上并没有）
     */
    public static String pwdtool(String u_pwd) {
        // 密码通过此方法解密并再加密
        return u_pwd;
    }

    /**
     * @param name 图片的路径，传入的路径是在图片存储位置之下的最高一级文件夹开始
     * @return 主机开头的路径
     */
    public static String imgNameRefactor(String name) {
        return Constants.IMG_PATH_HOST + name;
    }

    /**
     * 生成图片随机名称
     * 时间+UUID生成的随机字符串
     */
    public static String imgNameGenerator() {
        // 图片的随机名称，上传时间+UUID随机字符串
        String uuid = UUID.randomUUID().toString();
        String now = getDateString();
        return now + "_" + uuid;
    }

    /*
     * 删除图片的前缀，返回应当存储在服务器中的图片路径
     * 比如：
     * /root/webapp/myblog/static/img/avatar.png -> avatar.png
     * http://localhost:8000/img/avatar.png -> avatar.png
     */
    public static String delImgPrefix(String imgUrl) {

        if (imgUrl.startsWith(Constants.IMG_PATH_HOST_LOCAL)) {
            return imgUrl.substring(Constants.IMG_PATH_HOST_LOCAL.length());
        }

        if (imgUrl.startsWith(Constants.IMG_PATH_HOST_CLOUD)) {
            return imgUrl.substring(Constants.IMG_PATH_HOST_CLOUD.length());
        }

        if (imgUrl.startsWith(Constants.IMG_PATH_WIN)) {
            return imgUrl.substring(Constants.IMG_PATH_WIN.length());
        }

        if (imgUrl.startsWith(Constants.IMG_PATH_LIN)) {
            return imgUrl.substring(Constants.IMG_PATH_LIN.length());
        }

        return imgUrl;
    }

    /**
     * 判断字符串是否是空字符串(null或空白)
     */
    public static boolean isEmpty(String t_name) {
        return t_name == null || "".equals(t_name.trim());
    }

    /**
     * 根据当前的操作系统获取图片前缀
     * windows 下，返回Constatns.IMG_PATH_WIN
     * Linux 下，返回Constants.IMG_PATH_LIN
     */
    public static String getImgPrefix() {
        String osName = System.getProperties().getProperty("os.name");
        if (osName.startsWith("Win") || osName.startsWith("win")) {
            return Constants.IMG_PATH_WIN;
        } else {
            return Constants.IMG_PATH_LIN;
        }
    }

    /**
     * 根据当前的操作系统获取文件存储路径
     * windows 下，返回Constatns.BASE_PATH_WIN
     * Linux 下，返回Constants.BASE_PATH_LIN
     */
    public static String getBasePath() {
        String osName = System.getProperties().getProperty("os.name");
        if (osName.startsWith("Win") || osName.startsWith("win")) {
            return Constants.BASE_PATH_WIN;
        } else {
            return Constants.BASE_PATH_LIN;
        }
    }

    /**
     * 根据当前的操作系统，返回服务器图片请求前缀
     * windows下（本地）: 返回 Constants.IMG_PATH_HOST_LOCAL (http://localhost:8000/img/)
     * Linux下（云端）  : 返回 Constants.IMG_PATH_HOST_CLOUD (http://106.15.91.23:8000/img/)
     */
    public static String getHostImgPrefix() {
        String osName = System.getProperties().getProperty("os.name");
        if (osName.startsWith("Win") || osName.startsWith("win")) {
            return Constants.IMG_PATH_HOST_LOCAL;
        } else {
            return Constants.IMG_PATH_HOST_CLOUD;
        }
    }

    /**
     * 将服务器图片地址转为本地图片地址
     *
     * @param imgNameHost 如 http://localhost:8000/img/avatar.png
     * @return 图片的实际存储位置，如 /root/webapp/myblog/static/img/avatar.png
     */
    public static String imgName2Local(String imgNameHost) {
        String suffix = delImgPrefix(imgNameHost);
        return getImgPrefix() + suffix;
    }

    /**
     * 将制表符替换为4个空格
     */
    public static String replaceTabTo4Space(String content) {
        return content.replace("\t", "    ");
    }

    /**
     * 将数组转换为List
     */
    public static List<String> array2List(String[] array) {

        return new ArrayList<>(Arrays.asList(array));
    }

    /**
     * 删除指定路径的图片
     * 如：传入U2021072900000000001/avatar.png, 当前是在本地，则会寻找文件
     * D:/Documents/Projects/MyBlog/static/img/U2021072900000000001/avatar.png并删除
     */
    public static boolean deleteImgBypath(String path) {
        String forlderPath = getImgPrefix();
        String fullName = forlderPath + path;
        File file = new File(fullName);

        try {
            if (!file.exists()) {
                log.info("文件 " + fullName + " 不存在！");
                return false;
            } else {
                boolean ok = file.delete();
                if (ok) {
                    log.info("删除" + fullName + "成功！");
                } else {
                    log.info("删除" + fullName + "失败！");
                }
                return ok;
            }
        } catch (Exception e) {
            log.error("出现异常!");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据传入的url删除图片
     * 如：传入 http://106.15.91.23//
     */
    public static boolean deleteImgByUrl(String url) {
        String path = delImgPrefix(url);
        return deleteImgBypath(path);
    }

    /**
     * 检查密码的合法性
     */
    public static Result checkPwdValid(String pwd) {
        if (pwd == null || "".equals(pwd)) {
            return Result.fail(Result.ERR_CODE_BUSINESS, "密码不能为空！");
        }

//		char[] pwdArray = pwd.toCharArray();
//		for (int i = 0; i<pwdArray.length; i++) {
//			if (!Character.isAlphabetic(pwdArray[i]) && !Character.isDigit(pwdArray[i]) && !Constants.VALID_PWD_CHAR.contains(pwdArray[i])) {
//				return Result.fail(Result.ERR_CODE_BUSINESS, "密码中只能包含字母、数字、下划线、@、!、.、?");
//			}
//		}

        return Result.success();
    }

    public static <T> T loadJson(String jsonFilePath, Class<T> valueType) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(jsonFilePath);
        return objectMapper.readValue(file, valueType);
    }

    /**
     * 保存文件
     *
     * @param content  文件内容
     * @param filePath 文件保存路径
     */
    public static void saveFile(String content, String filePath) {
        try {
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }
            FileOutputStream stream = new FileOutputStream(filePath, false);
            stream.write(content.getBytes());
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 是否删除成功
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);

        boolean result = false;
        if (file.exists()) {
            result = file.delete();
        }
        return result;
    }
}
