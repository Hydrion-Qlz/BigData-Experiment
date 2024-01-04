package com.xjtu.xjtuse.assistance.SimilarWeb.impl;

import com.xjtu.xjtuse.assistance.SimilarWeb.dto.NewReplaceReqItem;
import com.xjtu.xjtuse.assistance.common.Constants;
import com.xjtu.xjtuse.assistance.common.Utils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ReplaceUtil {

    public static void main(String[] args) {
        String url = "http://www.baidu.com/";
        String filePath = "D:\\data\\code\\undergradeProject\\WebPageTransfer\\data\\segment\\explorads.com\\index.html";
        try {
            File input = new File(filePath);
            Document doc = Jsoup.parse(input, "UTF-8", ""); // 加载文件并解析
            System.out.println(doc.title()); // 输出网页标题

            Elements links = doc.select("a[href]"); // 选择所有带 href 属性的链接
            for (Element link : links) {
                System.out.println(link.attr("href")); // 输出链接 URL
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Document getDocument(String filePath) {
        File input = new File(filePath);
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return doc;
    }

    public static String makeReplace(String sourcePath, String targetPath, String saveFolderName, List<NewReplaceReqItem> replaceReqItemList) {
        System.out.println(sourcePath + " " + targetPath);
        Document sourceDocument = getDocument(sourcePath);
        Document targetDocument = getDocument(targetPath);

        for (NewReplaceReqItem replaceReqItem : replaceReqItemList) {
            Element sourceSegment = sourceDocument.select(replaceReqItem.getSource().getSegmentCssSelector()).first();
            Element targetSegment = targetDocument.select(replaceReqItem.getTarget().getSegmentCssSelector()).first();

            if (targetSegment == null || sourceSegment == null) {
                System.out.println("查找错误");
                System.out.println(replaceReqItem);
            } else {
                sourceSegment.parent().appendChild(targetSegment);
                sourceSegment.remove();
//                targetSegment.parent().appendChild(sourceSegment);
//                targetSegment.remove();
//                System.out.println(sourceSegment.text());
//                System.out.println(targetSegment.text());
//                targetSegment.text(sourceSegment.text());
            }
        }

        Path tempFilePath = Paths.get(Constants.TEMP_DIR, saveFolderName, "index.html");
        Utils.saveFile(targetDocument.toString(), tempFilePath.toString());

        // 设置Chrome浏览器驱动路径
        System.setProperty("webdriver.chrome.driver", Constants.DRIVER_PATH);
        // 创建Chrome浏览器实例
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // 无头模式
        WebDriver driver = new ChromeDriver(options);
        // 打开指定页面
        driver.get("file://" + tempFilePath);
        Long pageHeight = (Long) ((JavascriptExecutor) driver).executeScript(
                "return document.body.scrollHeight || document.documentElement.scrollHeight;");
        System.out.println(pageHeight);
        Dimension dimension = new Dimension(1980, Math.max(Math.toIntExact(pageHeight), 1080));
        driver.manage().window().setSize(dimension);

        // 查找需要截图的元素
        WebElement element = driver.findElement(By.tagName("body"));

        // 截图并保存到指定位置
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        Path folderPath = Paths.get(Constants.REPLACE_RESULT_SAVE_FOLDER, saveFolderName);
        try {
            Files.createDirectories(folderPath);
            String fileName = "screenshot.png";
            Path dest = Paths.get(folderPath.toString(), fileName);
            Files.deleteIfExists(dest);
            Files.copy(screenshotFile.toPath(), dest);
            // 关闭浏览器
//            driver.quit();
            return dest.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
