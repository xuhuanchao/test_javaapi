package com.xhc.test.collection;

import java.util.ArrayList;
import java.util.List;

public class TestPerformance {

    /**
     * 同样数据的array和list，计算遍历时长。
     * 1000000数据，array=5ms左右, list=9ms左右，平均array快4ms
     * 3000000数据量，array=4ms左右，list=11左右，array快7ms左右
     * @param args
     */
    public static void main(String[] args) {
        int length = 3000000;  // Integer.MAX_VALUE = 2147483647

        Integer[] array = new Integer[length];
        List<Integer> list = new ArrayList<>();


        for(int i=0; i<length; i++){
            array[i] = i;
            list.add(i);
        }


        long arrayStart = System.currentTimeMillis();
        System.out.println("array遍历开始时间：" +arrayStart);
        for(int j=0; j<length; j++){
            Integer integer = array[j];
        }
        long arrayEnd = System.currentTimeMillis();
        System.out.println("array遍历结束时间：" +arrayEnd + "，共花费：" + (arrayEnd - arrayStart));


        long listStart = System.currentTimeMillis();
        System.out.println("list遍历开始时间：" +listStart);
        for(int j=0; j<length; j++){
            Integer integer = list.get(j);
        }
        long listEnd = System.currentTimeMillis();
        System.out.println("list遍历结束时间：" +listEnd + "，共花费：" + (listEnd - listStart));
    }
}
