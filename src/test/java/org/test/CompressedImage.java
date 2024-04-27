package org.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author tomtian
 * @create 2023-03-17 11:07 AM
 * @Description
 */
public class CompressedImage {
    public static void main(String[] args) {
        // 指定目录路径
        File folder = new File("D:\\asdf");

        // 遍历目录下的所有文件
        for (File file : folder.listFiles()) {
            try {
                // 检查文件是否是图片格式
                String extension = getFileExtension(file);
                if (!extension.equalsIgnoreCase("jpg") && !extension.equalsIgnoreCase("jpeg")
                        && !extension.equalsIgnoreCase("png")) {
                    continue;
                }

                // 检查文件大小和分辨率
                BufferedImage image = ImageIO.read(file);
                Dimension dimension = new Dimension(image.getWidth(), image.getHeight());
                long size = file.length();
                if (dimension.width > 1920 && dimension.height > 1280 && size > 5 * 1024 * 1024) {
                    // 压缩图片
                    compressImage(file);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 获取文件扩展名
    private static String getFileExtension(File file) {
        String name = file.getName();
        int lastIndexOfDot = name.lastIndexOf(".");
        if (lastIndexOfDot == -1) {
            return "";
        }
        return name.substring(lastIndexOfDot + 1);
    }

    // 压缩图片
    private static void compressImage(File file) throws IOException {
        BufferedImage image = ImageIO.read(file);
        ImageIO.write(image, "jpg", file);
    }
}
