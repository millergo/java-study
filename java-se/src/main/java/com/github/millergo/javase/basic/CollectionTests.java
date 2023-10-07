package com.github.millergo.javase.basic;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * @author Miller Shan
 * @version 1.0
 * @since 2023/6/11 15:25:17
 */
public class CollectionTests {
    @Test
    void testCollection() {
        var list = new LinkedList<Integer>();
        list.add(3);
        list.add(4);
        list.add(2);
        Collections.sort(list, new Comparator<Integer>() {

            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

    }
}
