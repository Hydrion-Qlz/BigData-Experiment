from common import load_json


def scan_label_set(soup, label_set):
    label_set.add(soup.name)
    for child in soup.descendants:
        try:
            if child.name == None:
                if child.replace("\n", '').replace(" ", '') == "":
                    label_set.add("empty")
                else:
                    label_set.add("str")
            else:
                label_set.add(child.name)
        except:
            pass


def scan_label_set_and_encode(soup):
    label_set = set()

    scan_label_set(soup, label_set)

    encode_dict = {}
    i = 0
    for key in label_set:
        encode_dict[key] = str(i).zfill(3)
        i += 1
    return encode_dict


def compare_nodes(node1, node2):
    try:
        return node1.hash == node2.hash
    except Exception as e:
        pass
    return False


def tree_hash_and_merge(root, code):
    try:
        root.hash = code[root.name]
    except:
        root.hash = "999"

    for child in root.children:
        try:
            tree_hash_and_merge(child, code)
        except:
            pass

    # for idx, child1 in enumerate(root.children):
    #     for child2 in list(root.children)[idx + 1:]:
    #         if compare_nodes(child1, child2) and len(child1.hash) > 3:
    #             child2.sim_flag = True

    counter1 = 0
    for child1 in root.children:
        counter1 += 1
        counter2 = 0
        for child2 in root.children:
            counter2 += 1
            if counter2 <= counter1:
                continue
            if compare_nodes(child1, child2) and len(child1.hash) > 3:
                child2.sim_flag = True
    # child2.decompose()

    for child in root.children:
        try:
            if not child.sim_flag:
                root.hash = root.hash + "/" + child.hash
        except Exception as e:
            pass


def get_tree_hash(soup):
    # code = scan_label_set_and_encode(soup)
    encode = load_json("encode.json")
    tree_hash_and_merge(soup, encode)
    return soup.hash


def levenshtein_distance(hash1, hash2):
    if not hash1:
        return len(hash2 or '') or 0
    if not hash2:
        return len(hash1 or '') or 0

    size1 = len(hash1)
    size2 = len(hash2)

    last = 0
    tmp = list(range(size2 + 1))
    value = None

    for i in range(size1):
        tmp[0] = i + 1
        last = i

        for j in range(size2):
            if hash1[i] == hash2[j]:
                value = last
            else:
                value = 1 + min(last, tmp[j], tmp[j + 1])

            last = tmp[j + 1]
            tmp[j + 1] = value

    return value


def levenshtein_similarity(hash1, hash2):
    hash1 = hash1.split('/')
    hash2 = hash2.split('/')

    distance = levenshtein_distance(hash1, hash2)

    return distance / max(len(hash1), len(hash2))


def compare_tree_hash_similarity(hash1, hash2):
    differ_value = levenshtein_similarity(hash1, hash2)
    return float(format(1 - differ_value, '.4f'))
