package org.test;

import java.io.File;
import java.net.http.HttpClient;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author tomtian
 * @create 2022-08-31 14:42
 * @Description
 */
public class Test implements Runnable{

    @Override
    public void run() {
        System.out.println("xxx");
    }

    public static void main(String[] args) {
        //这个到底是3还是4
        //就是4
//        System.out.println(count22("22x22x2222"));
//        StringBuffer
//        Collections.synchronizedList()
//        Has/**/hMap
        System.out.println("D1C62D43C102D2C75D2B1AAD67C4DCC3".length());
        System.out.println(UUID.randomUUID().toString());
//        int[] array =
//        HttpClient httpClient = new H
        Thread thread =new Thread(()-> System.out.println("xx"));
        thread.start();
        thread.interrupt();
        thread.stop();
    }

    public static int count22(String str) {
        // TODO
        if (str==null|| str.length()<2){
            return 0;
            //这里有问题
        }else if (str.charAt(0)=='2' && str.charAt(1)=='2'){

            //这里有问题，应该有个参数来表达这个序号,越界了怎么办

                return 1+count22(str.substring(2));

        }else {

                return count22(str.substring(1));


        }
    }



}
