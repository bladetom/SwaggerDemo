package org.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tomtian
 * @create 2023-02-11 5:44 PM
 * @Description
 */
public class FindDuplicateFile {
    public static void main(String[] args) {
        File file =new File("F:\\BaiduNetdiskDownload\\娜美");
        List<File> files =new ArrayList<>();
        findAllFile(file,files);
        Map<Long,File> map =new HashMap<>();
        Map<File,File> duplicateFile =  new HashMap<>();
        for (File f:files
             ) {
            if (f.length()>1*1000*1000){
                if (!map.putIfAbsent(f.length(),f).equals(f)){
                    duplicateFile.put(f,map.get(f.length()));
                }
            }
            //应该有删除操作
        }
        for (Map.Entry entry:duplicateFile.entrySet()
             ) {
//            System.out.println(entry.getKey());
            System.out.println(entry.getKey().toString());
            System.out.println(entry.getKey().toString());
        }

    }

    public static void findAllFile(File file, List<File> fileList){

        for (File f:file.listFiles()
        ) {
            if (f.isDirectory()){
                findAllFile(f,fileList);
            }else {
                if (f.length()>1000*1000){
//                if (f.getName().contains("mp4")){
                    fileList.add(f);
                }
                System.out.println(f.toString());
            }
        }
    }
}
