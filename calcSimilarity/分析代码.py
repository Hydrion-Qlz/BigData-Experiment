import os.path
import time
from collections import defaultdict

from utils import *
from algorithm import HTMLSimilarity, html_similarity, simhash, tlsh, algorithm_list

page_list = read_csv('./data/pagelist.txt')
page_num = len(page_list)
save_path = 'data/result-clean.csv'


def get_html_path(lst):
    return os.path.join("clean_source", lst[0], lst[1], lst[1] + ".html")


class Content:
    def __init__(self):
        self.start_time = None
        self.value = [[0] * page_num for _ in range(page_num)]
        self.total_time = 0

    def start(self):
        self.start_time = time.time()

    def add_value(self, i, j, v):
        self.total_time += time.time() - self.start_time
        self.value[i][j] = "%.5f" % v
        self.value[j][i] = "%.5f" % v

    def get_total_time(self):
        return "%.2fs" % self.total_time


def save_result():
    result = []
    for alg_name, content in analysis_data.items():
        result.append([alg_name + " " + content.get_total_time()])
        result.extend(content.value)
    print(result)
    write_csv(result, save_path)


if __name__ == '__main__':
    start = time.time()
    analysis_data = defaultdict(Content)
    for i in range(page_num):
        for j in range(i, page_num):
            doc_content1 = read_file(get_html_path(page_list[i]))
            doc_content2 = read_file(get_html_path(page_list[j]))
            for alg in algorithm_list:
                analysis_data[alg.__name__].start()
                value = alg(doc_content1, doc_content2)
                analysis_data[alg.__name__].add_value(i, j, value)

    save_result()
    print("总用时: ", time.time() - start)
