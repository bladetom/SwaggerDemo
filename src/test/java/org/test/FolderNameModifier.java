package org.test;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//删除文件夹名词里面的多个[][],例如：[10p100mb][10p100mb]
public class FolderNameModifier {

    public static void main(String[] args) {
        // 获取当前工作目录
//        String directoryPath = System.getProperty("user.dir");
        File directory = new File("D:\\coser\\鳗鱼霏儿.part1\\鳗鱼霏儿");

        // 遍历目录下的所有文件和文件夹
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                // 检查是否是文件夹
                if (file.isDirectory()) {
                    String newName = removeFirstBrackets(file.getName());
                    if (!newName.equals(file.getName())) { // 仅在名称被修改时重命名
                        renameFolder(file, newName);
                    }
                }
            }
        }
    }

    private static String removeFirstBrackets(String name) {
        // 检查是否包含至少两个[]
        Pattern pattern = Pattern.compile("\\[.*?\\].*\\[.*?\\]");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()) {
            // 使用正则表达式删除第一个[]及其内容
            return name.replaceFirst("\\[.*?\\]", "");
        }
        return name;
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
