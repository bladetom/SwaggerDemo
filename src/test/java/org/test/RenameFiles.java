package org.test;

import java.io.File;

public class RenameFiles {
    public static void main(String[] args) {
        String path = "D:\\BaiduNetdiskDownload\\52cos 017"; // 指定需要修改名称的文件夹路径
        File dir = new File(path);
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                String oldName = file.getName(); // 原文件夹名称
                //更改名字
//                int index = oldName.indexOf("]");
//                String newStr = oldName.substring(0,index+1);
                long size = getDirSize(file); // 获取文件夹大小

                double visizableSize = (double)size/1024/1024;
                String formattedResult = String.format("%.2f", visizableSize); // 格式化结果，保留两位小数
                String newName = oldName + " " + formattedResult + "MB"; // 新文件夹名称
                file.renameTo(new File(dir.getAbsolutePath() + "\\" + newName)); // 修改文件夹名称
            }
        }
    }

    // 获取文件夹大小
    public static long getDirSize(File dir) {
        long size = 0;
        File[] files = dir.listFiles();

        for (File file : files) {
            if (file.isFile()) {
                size += file.length();
            } else {
                size += getDirSize(file);
            }
        }
        return size;
    }
}