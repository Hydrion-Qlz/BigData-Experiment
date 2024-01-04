import os
import shutil

def delete_git_folders(folder_path):
    for root, dirs, files in os.walk(folder_path):
        for dir_name in dirs:
            if dir_name == ".git":
                git_folder_path = os.path.join(root, dir_name)
                print("Deleting", git_folder_path)
                shutil.rmtree(git_folder_path)

# 指定要删除 .git 文件夹的根文件夹路径
folder_path = "./calcSimilarity"

# 调用函数删除 .git 文件夹
delete_git_folders(folder_path)