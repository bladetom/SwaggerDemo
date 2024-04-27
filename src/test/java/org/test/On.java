package org.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/*
*
* */
public class On {
    private static final HashMap<Integer, Integer> map = new HashMap<>();

    public static void main(String[] args) {
        map.put(1,1);
        test("thread1");
        test("thread2");

    }
    public static void test(String threadName){
        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName()+":"+i);
            }
        },threadName).start();
    }
}
