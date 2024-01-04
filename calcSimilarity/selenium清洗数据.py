from selenium import webdriver
from utils import *
from selenium.webdriver.chrome.options import Options

if __name__ == '__main__':
    page_list = read_csv("./data/pagelist.txt")
    options = Options()
    options.add_argument('lang=zh_CN.UTF-8')
    driver_path = r"C:\Program Files\Google\Chrome\Application\chromedriver.exe"
    driver = webdriver.Chrome(driver_path, options=options)
    for page in page_list:
        # html_file_path = get_selenium_file_path(page)
        # print(html_file_path)
        # driver.get(html_file_path)
        driver.get(page[2])
        driver.execute_script("""
            function traverse(node, deleteNodeList) {
                if (!node) {
                    return;
                }
                if (node.tagName !== "SCRIPT" && node.tagName !== "STYLE" && getComputedStyle(node).display === 'none') {
                    deleteNodeList.push(node)
                    return;
                }
                for (let child of node.children) {
                    traverse(child, deleteNodeList);
                }
            }
            
            function deleteNode(node) {
                const deleteNodeList = [];
                traverse(node, deleteNodeList);
                deleteNodeList.forEach(item => {
                    console.log(item);
                    item.remove();
                })
                console.log(deleteNodeList.length);
            }
            
            deleteNode(document.body)
            """)
        clean_source = driver.page_source
        save_file(clean_source, get_clean_file_path(page))
        # break
