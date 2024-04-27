package org.test;

import java.io.File;

public class DeleteEmptyFolders {

    public static void main(String[] args) {
        File directory = new File("D:\\coser\\神楽板真冬");
        deleteEmptyFolders(directory);
    }

    public static void deleteEmptyFolders(File directory) {
        if (directory.isDirectory()) {
            // 获取目录中的所有子目录和文件
            File[] files = directory.listFiles();
            // 如果目录为空，则将其删除
            if (files != null && files.length == 0) {
                directory.delete();
            } else {
                // 递归处理子目录
                for (File file : files) {
                    deleteEmptyFolders(file);
                }
                // 重新获取目录中的所有子目录和文件
                files = directory.listFiles();
                // 如果目录仍为空，则将其删除
                if (files != null && files.length == 0) {
                    directory.delete();
                }
            }
        }
    }
}
