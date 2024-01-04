import argparse
import os
import os.path

from segment import Segment


# os.environ["http_proxy"] = "http://127.0.0.1:14244"
# os.environ["https_proxy"] = "https://127.0.0.1:14244"

def get_args():
    parser = argparse.ArgumentParser()
    parser.add_argument("url", help="待处理的网页链接")
    parser.add_argument("dictionary_name", help="页面保存路径文件夹名称")
    parser_arg = parser.parse_args()
    return parser_arg.url, parser_arg.dictionary_name


def get_valid_folder_name(dict_name):
    idx = 1
    path = f"data/{dict_name}"
    temp_path = path
    while os.path.exists(temp_path):
        temp_path = "%s_%d" % (path, idx)
        idx += 1
    return temp_path


if __name__ == "__main__":
    url, folder_name = get_args()
    folder_name = get_valid_folder_name(folder_name)
    print(url, folder_name)
    spliter = Segment()
    spliter.segment(url=url, output_folder=folder_name,
                    is_output_images=False)
