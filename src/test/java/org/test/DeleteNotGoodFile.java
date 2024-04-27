package org.test;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

//多重文件他删不了
public class DeleteNotGoodFile {
    public static void main(String[] args) {
        String directoryPath = "D:\\coser\\miko酱ww"; // 替换为你的目录路径
        File directory = new File(directoryPath);
        deleteNonVideoFilesInSpecificFolder(directory, "不行");
    }

    private static void deleteNonVideoFilesInSpecificFolder(File folder, String specificName) {
        if (folder.isDirectory()) {
            // 检查文件夹名是否包含特定字符串
            if (folder.getName().contains(specificName)) {
                deleteNonVideoFiles(folder);
            } else {
                // 递归检查子文件夹
                File[] subFiles = folder.listFiles();
                if (subFiles != null) {
                    for (File subFile : subFiles) {
                        deleteNonVideoFilesInSpecificFolder(subFile, specificName);
                    }
                }
            }
        }
    }

    private static void deleteNonVideoFiles(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && !isVideoFile(file)) {
                    deleteFile(file);
                } else if (file.isDirectory()) {
                    deleteNonVideoFiles(file); // 递归删除子文件夹中的非视频文件
                }
            }
        }
    }

    private static boolean isVideoFile(File file) {
        Set<String> videoExtensions = new HashSet<>();
        videoExtensions.add("mp4");
        videoExtensions.add("avi");
        videoExtensions.add("mov");
        videoExtensions.add("wmv");
        // 添加其他视频格式的扩展名

        String fileName = file.getName();
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            String extension = fileName.substring(i + 1).toLowerCase();
            return videoExtensions.contains(extension);
        }
        return false;
    }

    private static void deleteFile(File file) {
        if (file.delete()) {
            System.out.println("Deleted: " + file.getPath());
        } else {
            System.out.println("Failed to delete: " + file.getPath());
        }
    }
}
