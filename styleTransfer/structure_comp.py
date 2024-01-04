import argparse

from common import *
from treeHash import compare_tree_hash_similarity


def get_args():
    parser = argparse.ArgumentParser()
    parser.add_argument("source", help="原网页")
    parser.add_argument("target", help="目标网页")
    parser_arg = parser.parse_args()
    return parser_arg.source, parser_arg.target


def get_result_file_content(folder_name):
    path = f"data/{folder_name}/result.json"
    data = load_json(path)
    return data


class Item:
    def __init__(self, source_segment_id, target_segment_id, similarity):
        self.source_segment_id = source_segment_id
        self.target_segment_id = target_segment_id
        self.similarity = similarity

    def __str__(self):
        return f"{self.source_segment_id} -> {self.target_segment_id} : {self.similarity}"

    def __repr__(self):
        return f"{self.source_segment_id} -> {self.target_segment_id} : {self.similarity}"


def remove_item(items, max_similarity_item):
    for item in items[:]:
        if item.source_segment_id == max_similarity_item.source_segment_id or \
                item.target_segment_id == max_similarity_item.target_segment_id:
            items.remove(item)


if __name__ == '__main__':
    source, target = get_args()
    source_content = get_result_file_content(source)
    target_content = get_result_file_content(target)

    items = []

    for source_segment in source_content['segments']:
        for target_segment in target_content['segments']:
            value = compare_tree_hash_similarity(source_segment['hash'], target_segment['hash'])
            items.append(Item(source_segment['segment_id'], target_segment['segment_id'], value))

    replace_num = min(len(source_content['segments']), len(target_content['segments']))
    print(len(source_content['segments']), len(target_content['segments']))
    dct = {
        'sourceInfo': {
            'url': source_content['url'],
            'title': source_content['title']
        },
        'targetInfo': {
            'url': target_content['url'],
            'title': target_content['title']
        },
        'replaceInfo': []
    }
    while replace_num > 0:
        max_similarity_item = max(items, key=lambda x: x.similarity)
        print(max_similarity_item)
        remove_item(items, max_similarity_item)
        dct['replaceInfo'].append({
            'sourceCssSelector': source_content['segments'][max_similarity_item.source_segment_id]['css_selector'],
            'targetCssSelector': target_content['segments'][max_similarity_item.target_segment_id]['css_selector'],
        })
        replace_num -= 1
    path = f"data/replace/{source}-{target}.json"
    prepare_dir(os.path.dirname(path))
    save_json(path, dct)
