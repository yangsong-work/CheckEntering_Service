package com.fri.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestD {
    public static void main(String[] args) {
   String a = "green";
   String b = "yellow";
   String c = "red";
   List list = new ArrayList<>();
        list.add(a);
        list.add(b);
        list.add(c);

        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.substring(o1.length()-1,o1.length()).compareTo(o2.substring(o2.length()-1,o2.length()));
            }
        });
        System.out.println(list);
    }
}
