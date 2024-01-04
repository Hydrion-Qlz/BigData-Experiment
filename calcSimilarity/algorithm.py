import treelib

from utils import *
from Structure_comp import *


def HTMLSimilarity(html_doc1, html_doc2):
    """
    https://github.com/SPuerBRead/HTMLSimilarity
    :param html_doc2:
    :param html_doc1:
    :return: similar value
    """
    from HTMLSimilarity.htmlsimilarity import get_html_similarity
    # differ_value < 0.2时相似，differ_value > 0.2时不相似
    is_similarity, differ_value = get_html_similarity(html_doc1, html_doc2)
    # return 1 / differ_value if differ_value != 0 else 0
    # return differ_value
    return 1 / (1 + differ_value)


def html_similarity(html_doc1, html_doc2):
    """
    https://github.com/matiskay/html-similarity
    :param html_doc1:
    :param html_doc2:
    :return: similarity value
    """
    from html_similarity import style_similarity
    value = style_similarity(html_doc1, html_doc2)
    return value


def html_differ(html_doc1, html_doc2):
    """
    https://github.com/bem/html-differ based on nodejs
    :param html_doc1:
    :param html_doc2:
    :return:
    """


def simhash(html_doc1, html_doc2):
    """
    https://github.com/1e0ng/simhash
    :param html_doc1:
    :param html_doc2:
    :return:
    """
    from simhash import Simhash
    a_simhash = Simhash(html_doc1)
    b_simhash = Simhash(html_doc2)
    max_hashbit = max(len(bin(a_simhash.value)), len(bin(b_simhash.value)))
    # 计算汉明距离
    distance = a_simhash.distance(b_simhash)
    similar = 1 - distance / max_hashbit
    return similar


def tlsh(html_doc1, html_doc2):
    """
    https://github.com/trendmicro/tlsh
    :param html_doc1: str
    :param html_doc2: str
    :return:
    """
    import tlsh

    s1 = tlsh.hash(get_bytes(html_doc1))
    s2 = tlsh.hash(get_bytes(html_doc2))
    # todo: 这里相似度正则化的时候有问题
    differ_value = tlsh.diff(s1, s2)
    return 1 / differ_value if differ_value != 0 else 1


def zss(html_doc1, html_doc2):
    """
    https://github.com/timtadh/zhang-shasha
    :param html_doc1:
    :param html_doc2:
    :return:
    """
    # todo: zss using two tree to compare, so the first thing is convert the html string to DOM tree
    import zss
    try:
        from editdist import distance as strdist
    except ImportError:
        def strdist(a, b):
            if a == b:
                return 0
            else:
                return 1

    def weird_dist(A, B):
        return 10 * strdist(A, B)

    class WeirdNode(treelib.Node):
        def __init__(self):
            super().__init__()
            # self.my_label = label
            # self.my_children = list()

        @staticmethod
        def get_children(node):
            return node.children

        @staticmethod
        def get_label(node):
            return node.data.label

        def addkid(self, node, before=False):
            if before:
                self.my_children.insert(0, node)
            else:
                self.my_children.append(node)
            return self

    dom1 = parser_dom(html_doc1)
    dom2 = parser_dom(html_doc2)
    dist = zss.simple_distance(dom1, dom2, lambda node: node.children, lambda node: node.label, weird_dist)
    return dist


def PyGram(html_doc1, html_doc2):
    """
    https://github.com/WittleWolfie/PyGram
    :param html_doc1:
    :param html_doc2:
    :return:
    """
    # todo: using two tree to compare


def apted(html_doc1, html_doc2):
    """
    https://github.com/JoaoFelipe/apted
    :param html_doc1:
    :param html_doc2:
    :return:
    """
    # todo: using two tree to compare


def structure_compare(html_doc1, html_doc2):
    """
    :param html_doc1:
    :param html_doc2:
    :return: similarity value
    """
    return 1 - structure_comp(html_doc1, html_doc2)


def test_struct_compare():
    content1 = read_file("source/邮箱/qq邮箱/qq邮箱.html")
    content2 = read_file("source/邮箱/163邮箱/163邮箱.html")
    content2 = read_file("source/邮箱/新浪邮箱/新浪邮箱.html")
    # content2 = read_file("source/邮箱/163邮箱/163邮箱.html")
    print(structure_compare(content1, content1))


def test_dom_parser():
    content1 = read_file("source/邮箱/qq邮箱/qq邮箱.html")
    dom = parser_dom(content1)
    print(dom)


def test_zss():
    content = read_file("source/邮箱/qq邮箱/qq邮箱.html")
    value = zss(content, content)
    print(value)


def test_node_num():
    page_list = read_csv("./data/pagelist.txt")
    lst = [["网页", "旧节点数", "预处理后节点数"]]
    for page in page_list:
        old_path = get_html_file_path(page)
        old_content = read_file(old_path)
        old_node_num = get_node_num(old_content)
        clean_path = get_clean_file_path(page)
        clean_content = read_file(clean_path)
        clean_node_num = get_node_num(clean_content)
        lst.append([page[1], old_node_num, clean_node_num])
    write_csv(lst, "./data/nodeNum.csv")


algorithm_list = [
    HTMLSimilarity,
    html_similarity,
    simhash,
    tlsh,
    structure_compare
]
