import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np
from pylab import mpl
import pandas as pd
from utils import *
from algorithm import algorithm_list

mpl.rcParams["font.sans-serif"] = ["SimHei"]
mpl.rcParams["axes.unicode_minus"] = False

if __name__ == '__main__':
    data = read_csv("./data/result-clean.csv")
    page_list = [a[1] for a in read_csv('./data/pagelist.txt')]
    page_num = len(page_list)
    # algorithm_num = len(algorithm_list)
    dct = {}
    print(data)
    print(page_list)

    # 读取并预处理数据
    for i in range(0, len(data), 13):
        algorithm_desc = data[i][0]
        # algorithm_name = algorithm_desc.split(" ")[0]
        print(algorithm_desc)
        dct[algorithm_desc] = pd.DataFrame(data[i + 1:i + 13], columns=page_list, index=page_list, dtype=float)
        print(dct[algorithm_desc])
        # break

    # 生成热力图
    plt.figure(figsize=(18, 12))

    idx = 0
    for name, value in dct.items():
        idx += 1
        ax1 = plt.subplot(2, 3, idx)
        plt.title(name)
        sum_val = value.sum().sum()
        avg = (sum_val - page_num) / (page_num * page_num)
        mask = value < avg
        sns.heatmap(value, cmap='Blues', annot=True, fmt='.2f', mask=mask)
        rect1 = plt.Rectangle((0, 0), 4, 4, facecolor='#ffffff00', edgecolor='red')
        rect2 = plt.Rectangle((4, 4), 4, 4, facecolor='#ffffff00', edgecolor='red')
        rect3 = plt.Rectangle((8, 8), 4, 4, facecolor='#ffffff00', edgecolor='red')
        ax1.add_patch(rect1)
        ax1.add_patch(rect2)
        ax1.add_patch(rect3)
    plt.tight_layout()
    plt.savefig('./data/Figure-clean-selenium.png')
    plt.show()
