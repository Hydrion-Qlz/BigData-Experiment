import os
import csv
import sys
from treelib import Tree
from bs4 import BeautifulSoup
import bs4

sys.path.append("HTMLSimilarity")


def make_dir(path):
    """
    传入文件路径，生成父级文件夹(open模块不会自动生成文件夹，会报错)
    :param path:
    :return:
    """
    dir_name = os.path.dirname(path)
    if not os.path.exists(dir_name):
        os.makedirs(dir_name)


def write_csv(data, path):
    make_dir(path)
    with open(path, mode='w', encoding='utf-8', newline='') as f:
        writer = csv.writer(f)
        writer.writerows(data)


def read_csv(path):
    lst = []
    with open(path, mode='r', encoding='utf-8') as f:
        reader = csv.reader(f)
        for line in reader:
            lst.append(line)
    return lst


def get_bytes(s):
    """
    将字符串转换为字节流
    :param s:
    :return:
    """
    return bytes(s, encoding='utf-8')


def encode(s):
    """
    将字符串转换为二进制
    :param s:
    :return:
    """
    return ''.join([bin(ord(c)).replace('0b', '') for c in s])


def read_file(path: str) -> str:
    with open(path, mode='r', encoding='utf-8') as f:
        content = f.read()
        return content


def save_file(data, path):
    make_dir(path)
    with open(path, mode='w', encoding='utf-8') as f:
        f.write(data)


def get_all_html_file(base_path):
    for root, dirs, files in os.walk(base_path, topdown=False):
        for name in files:
            if name.endswith(".html"):
                print(os.path.join(root, name))


def get_html_file_path(lst):
    return os.path.join("source", lst[0], lst[1], lst[1] + ".html")


def get_clean_file_path(lst):
    return os.path.join("clean_source", lst[0], lst[1], lst[1] + ".html")


def get_selenium_file_path(lst):
    return "file://" + os.path.join(os.getcwd(), get_html_file_path(lst))


class DOMTree:
    def __init__(self, label, attrs):
        self.label = label
        self.attrs = attrs


class HTMLParser:
    def __init__(self, html):
        self.dom_id = 1
        self.dom_tree = Tree()
        self.bs_html = BeautifulSoup(html, 'lxml')

    def get_dom_structure_tree(self):
        for content in self.bs_html.contents:
            if isinstance(content, bs4.element.Tag):
                self.bs_html = content
        self.recursive_descendants(self.bs_html, 1)
        return self.dom_tree

    def recursive_descendants(self, descendants, parent_id):
        if self.dom_id == 1:
            self.dom_tree.create_node(descendants.name, self.dom_id, data=DOMTree(descendants.name, descendants.attrs))
            self.dom_id = self.dom_id + 1
        for child in descendants.contents:
            if isinstance(child, bs4.element.Tag):
                self.dom_tree.create_node(child.name, self.dom_id, parent_id, data=DOMTree(child.name, child.attrs))
                self.dom_id = self.dom_id + 1
                self.recursive_descendants(child, self.dom_id - 1)


def parser_dom(html_doc):
    parser = HTMLParser(html_doc)
    return parser.get_dom_structure_tree()


def get_node_num(html_doc):
    dom = parser_dom(html_doc)
    return len(dom.nodes)
    # print(dom)
