from bs4 import BeautifulSoup, Tag
from utils import *

page_list = read_csv('data/pagelist.txt')
page_num = len(page_list)
for i in range(page_num):
    if page_list[i][1] != '新浪邮箱':
        pass
    path = get_html_file_path(page_list[i])
    content = read_file(path)
    bs = BeautifulSoup(content, features='html.parser')
    count = 0
    new_bs = BeautifulSoup(features="html.parser")
    for child in bs.descendants:
        if isinstance(child, Tag) and (
                'display: none' in child.attrs.get('style', '') or 'display:none' in child.attrs.get(
            'style', '')):
            # print(child)
            count += 1
            child.clear()
    save_file(str(bs), get_clean_file_path(page_list[i]))
    print(page_list[i][1], count)
