package org.test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tomtian
 * @create 2023-03-27 10:17 AM
 * @Description
 */
public class MoveTest {
    public static void main(String[] args) {
        List<File> list = new ArrayList<>();
//        find(new File("E:\\朱可儿(hjhfb.top)\\JG"),list);
        File file = new File("E:\\朱可儿(hjhfb.top)\\JG\\1-200");
        for (File f:file.listFiles()
             ) {
            if (f.isDirectory()){
                find(f,list);
            }
        }
        System.out.println(list);
    }

    public static void find(File file,List<File> list){
        int size = file.listFiles().length;
        if (size>1){
            list.add(file);
        }else {
            File subFile = file.listFiles()[0];
            if (subFile.isDirectory()){
                find(subFile,list);
            }
        }
    }

    public static void MoveFolder(){

    }
}
