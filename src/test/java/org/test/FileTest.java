package org.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tomtian
 * @create 2023-01-23 12:16 PM
 * @Description
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
//        test("F:\\色情\\91斯文禽兽\\v");
//        size("E:\\[鹿初]调教\\视频\\sjhs04.com_0001.mp4");
//        String dest = "F:\\迅雷下载\\小仙女思妍39部\\";
//        List<String> list = new ArrayList<>();
//        findsubFile("F:\\迅雷下载\\唐 先 生",list);
//        for (String str:list
//             ) {
//            copyFileUsingFileChannels(new File(str),new File("F:\\迅雷下载\\唐 先 生"));
//        }
//        copyFileUsingFileChannels(new File("F:\\迅雷下载\\小仙女思妍39部\\1 (2)\\a (1).mp4"),new File("F:\\迅雷下载\\小仙女思妍39部\\2.mp4"));
        List<File> files = new ArrayList<>();
        String dest = "D:\\BaiduNetdiskDownload\\亚马逊鲶鱼 [8套-494P-4V-6.83G]";
        findSubFile(dest,files);
        for (File f:files
             ) {
            copyFileUsingFileChannels(f,new File(dest+"\\"+f.getName()));
            if (!f.delete()) {
                System.out.println(f.toString()+"删除失败");
            }
        }
    }

    public static void test(String path){
        File file =  new File(path);
        File[] files = file.listFiles();
        for (File f:files
        ) {
            if (f.isFile()){
                System.out.println(f.getUsableSpace());
                System.out.println(f.getFreeSpace());
            }

        }
    }

    public static void findAllFile(File file, List<File> fileList){

        for (File f:file.listFiles()
        ) {
            if (f.isDirectory()){
                findAllFile(f,fileList);
            }else {
                if (f.getName().contains("mp4")&&f.length()>1024*1024*100){
                    System.out.println(f.toString());
                    fileList.add(f);
                }
//                System.out.println(f.toString());
            }
        }
    }

    public static void size(String path){
        File file = new File(path);
        System.out.println(file.getTotalSpace()+"-"+file.getFreeSpace()+"-"+file.getUsableSpace());
        System.out.println(file.length()/1024/1024+"MB");
    }
    public static void delete(String path){
        File file =  new File(path);
        if (file.delete()){
            System.out.println(file+"have been deleted");
        }else {
            System.out.println("fail to delete");
        }
    }


    private static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        System.out.println("正在搬运"+source.toString());
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    public static void findsubFile(String directory, List<String> list){
        File file = new File(directory);
        File[] subFile = file.listFiles();
        for (File f:subFile
             ) {
            if (f.isDirectory()){
            File[] secondFile = f.listFiles();
            for (int i = 0; i < secondFile.length; i++) {
                if (secondFile[i].isFile()) {
                    //加入其他视频后缀，mkv，ts mov
                    if (secondFile[i].getName().contains("mp4")){
                        list.add(secondFile[i].toString());
                    }
                }
            }
            }
        }
    }

    public static void findSubFile(String directory ,List<File> files){
        File file = new File(directory);
        for (File f:file.listFiles()
             ) {
            if (f.isDirectory()){
                for (File innerFile:f.listFiles()
                ) {
                    //这里改一下就行了，无论是视频还是文本
//                System.out.println(innerFile.toString()+"----"+innerFile.getName());
                    if (innerFile.getName().contains("TAR")){
                        files.add(innerFile);
                    }

                }
            }

        }
    }



}
