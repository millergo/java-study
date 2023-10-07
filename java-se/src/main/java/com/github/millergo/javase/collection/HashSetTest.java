package com.github.millergo.javase.collection;

import com.github.millergo.javase.jdk8.User;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author miller
 */
public class HashSetTest {
    @Test
    void testHashSet() {
        User user = new User();
        user.setName("Miller");
        user.setAge(30);

        User user1 = new User();
        user1.setName("Mila");
        user1.setAge(7);

        Set<User> userSet = new HashSet<>();
        userSet.add(user);
        userSet.add(user1);

        userSet.forEach(System.out::println);
        System.out.println("---------------");
        // 操作的是引用，所以引用关联的对象值也会跟着改变
        user1.setAge(8);
        /* Set 底层是HashMap，每次调用add方法的时候都会调用map.put(key,value)，
        而map每次put的时候都会调用hashcode()方法生成唯一的hash值，所以第二次添加的时候认为是一个新对象，
        但是又由于这个新对象的引用指向的是同一个对内存空间所以新对象的修改会导致老对象的堆内存一并修改。
         */
        userSet.add(user1);
        userSet.forEach(System.out::println);
        System.out.println(userSet.size());

        var map = new HashMap<String, User>();
        map.put("Miller", user);
        // map.put("Miller", user);
        map.forEach((key, user2) -> System.out.println(user2));
    }
}
