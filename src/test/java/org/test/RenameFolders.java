package org.test;

import java.io.File;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

/*
* 在每个文件夹后面添加nPmVkMB
* */
public class RenameFolders {
    public static void main(String[] args) {
        File directory = new File("D:\\韩国模特\\金提莫 - 全套 34G合集");
        File[] files = directory.listFiles();
        //如果在二级文件夹里面，计算会出现问题
        for (File file : files) {
            if (file.isDirectory()) {
                String str = file.getName();
                String regex = "(\\d+[pP])" + ".*" + "(\\d+[mMgGbB]{2})";

                //有问题，这里应该直接回去
                if (Pattern.compile(regex).matcher(str).find()) {
                    continue;
                }

                String oldName = file.getName();
                int numPics = 0;
                int numVideos = 0;
                long folderSize = 0;

                for (File subFile : file.listFiles()) {
                    if (subFile.isFile()) {
                        String extension = subFile.getName().substring(subFile.getName().lastIndexOf(".") + 1).toLowerCase();
                        if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png")) {
                            numPics++;
                        } else if (extension.equals("mp4") || extension.equals("avi") || extension.equals("mov")) {
                            numVideos++;
                        }
                        folderSize += subFile.length();
                    }
                }


                String newName = oldName + "_" + numPics + "P " + numVideos + "V " + formatSize(folderSize);
                StringBuffer sb = new StringBuffer(oldName);
                sb.append("[");
                if (numPics!=0){
                    sb.append(numPics+"P ");
                }
                if (numVideos!=0){
                    sb.append(numVideos+"V ");
                }
                sb.append(formatSize(folderSize));
                sb.append("]");
                file.renameTo(new File(directory, sb.toString()));
            }
        }
    }

    public static String modifyString(String str) {
        Pattern pPattern = Pattern.compile("\\d+[pP]");
        Pattern mbPattern = Pattern.compile("\\d+[mM][bB]");

        boolean containsP = pPattern.matcher(str).find();
        boolean containsMB = mbPattern.matcher(str).find();
        //有问题，这里应该直接回去
        if (containsP && containsMB) {
            return str + "xxmb";
        }
        return str;
    }
    public static String formatSize(long size) {
        String hrSize = null;

        double k = size / 1024.0;
        double m = size / 1048576.0;
        double g = size / 1073741824.0;
        double t = size / 1099511627776.0;

        DecimalFormat dec = new DecimalFormat("0.00");

        if (t > 1) {
            hrSize = dec.format(t).concat("TB");
        } else if (g > 1) {
            hrSize = dec.format(g).concat("GB");
        } else if (m > 1) {
            hrSize = dec.format(m).concat("MB");
        } else if (k > 1) {
            hrSize = dec.format(k).concat("KB");
        } else {
            hrSize = dec.format(size).concat("B");
        }

        return hrSize;
    }
}
