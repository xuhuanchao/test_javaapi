package com.xhc.test.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

/**
 * Collections.singletonList()  单元素的集合，不可修改。只占一个元素的空间
 * Collections.unmodifiableList()  多元素集合，不可修改、添加。
 *
 * @Author: xhc
 * @Date: 2020/3/24 9:34
 * @Description:
 */
public class TestCollections {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("中国");
        list.add("日本");
        list.add("韩国");

        // 只有单个元素的集合，节省空间，不能修改
        List<String> singletonList = Collections.singletonList("zhangsan");
        String s = singletonList.get(0);
        // 不可以修改、添加、删除
        //list.set(0, "lisi");


        //不可以修改的集合，只可以用来查看
        List<String> unmodifiableList = Collections.unmodifiableList(list);
        ListIterator<String> iterator = unmodifiableList.listIterator();
        while(iterator.hasNext()){
            String next = iterator.next();
            System.out.println(next);
        }

        //不可以添加，修改
        //unmodifiableList.add("泰国");
    }
}
