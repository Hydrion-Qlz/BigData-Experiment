from selenium import webdriver
from selenium.webdriver.chrome.options import Options
import csv
from utils import *

# 构造webdriver
driver_path = r"C:\Program Files\Google\Chrome\Application\chromedriver.exe"
options = Options()
options.add_argument('--headless')
driver = webdriver.Chrome(driver_path, options=options)


def save_page(kind, name, url):
    driver.get(url)
    save_path = f"source/{kind}/{name}/{name}"
    make_dir(save_path)

    # 仅首页图片(未滚动) 1920x1080
    driver.set_window_size(1920, 1680)
    driver.get_screenshot_as_file(save_path + "_short.png")

    # 整体截图(带滚动)
    width = driver.execute_script("return document.documentElement.scrollWidth")
    height = driver.execute_script("return document.documentElement.scrollHeight")
    driver.set_window_size(width, height)
    driver.get_screenshot_as_file(save_path + "_full.png")

    # 保存为html
    source_code = driver.page_source
    with open(save_path + ".html", mode='w', encoding='utf-8') as html_file:
        html_file.write(source_code)

    # 保存为mhtml
    res = driver.execute_cdp_cmd('Page.captureSnapshot', {})
    # 2. write file locally
    with open(save_path + ".mhtml", 'w', newline='') as sf:
        sf.write(res['data'])


if __name__ == '__main__':
    # 打开所有网页列表
    with open("pagelist.txt", mode='r', encoding='utf-8') as f:
        csv_reader = csv.reader(f)
        for line in csv_reader:
            print(line)
            save_page(line[0], line[1], line[2])
    # save_page("搜索引擎", "google", "https://www.google.com.hk")
    driver.quit()
