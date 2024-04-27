package org.test;

import java.io.File;

public class RenameNotGoodFolder {

    public static void main(String[] args) {
        // 获取当前工作目录
//        String directoryPath = System.getProperty("D:\\coser\\鳗鱼霏儿.part1\\鳗鱼霏儿");
        File directory = new File("D:\\coser\\鳗鱼霏儿.part1\\鳗鱼霏儿");

        // 遍历目录下的所有文件和文件夹
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                // 检查是否是文件夹
                if (file.isDirectory()) {
                    renameFolder(file, file.getName() + "不行");
                }
            }
        }
    }

    private static void renameFolder(File folder, String newName) {
        // 创建新的文件路径对象
        File newFile = new File(folder.getParent(), newName);
        // 重命名文件夹
        boolean success = folder.renameTo(newFile);
        if (success) {
            System.out.println("Folder renamed to: " + newName);
        } else {
            System.out.println("Failed to rename folder: " + folder.getName());
        }
    }
}
