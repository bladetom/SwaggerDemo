package org.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tomtian
 * @create 2023-02-09 6:04 PM
 * @Description
 */
public class Rename {
    public static void main(String[] args) {
//        List<File> list =new ArrayList<>();
//        findEmptyFolder(new File("D:\\coser\\抱走莫子aa写真合集"),list);
//        System.out.println(list);
//        Rename();
        List<File> list = new ArrayList<>();
        findAllFile(new File("D:\\BaiduNetdiskDownload\\24041205"),list);
        for (File f:list
             ) {
            //这是删除带中文的zip例如：abc.zi删除p
            //f.renameTo(new File(removeChineseCharacters(f.toString())));
            f.renameTo(new File(f.toString()+".7z"));
        }
    }

    public static void findAllFile(File file,List<File> fileList){

        for (File f:file.listFiles()
        ) {
            if (f.isDirectory()){
                findAllFile(f,fileList);
            }else {
                if (f.getName().contains("")){
                    fileList.add(f);
                }
//                System.out.println(f.toString());
            }
        }
    }
    public static  String removeChineseCharacters(String input) {
        return input.replaceAll("[\\p{IsHan}]", "");
    }

    public static void Rename(){
        File file = new File("");
        for (File f:file.listFiles()
        ) {
            if (f.isFile()&&f.getName().contains(".zip")){
                f.renameTo(new File(f.toString()+".7z"));
            }
        }
    }

    public static void findEmptyFolder(File file,List<File> fileList){

        for (File f:file.listFiles()
        ) {
            if (f.isDirectory()){

                if (f.listFiles().length==0){
                    fileList.add(f);
                    f.delete();
                }
                findAllFile(f,fileList);
            }
        }
    }
}
