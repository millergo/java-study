package com.github.millergo.javase.collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.util.*;

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

@AllArgsConstructor
@NoArgsConstructor
@Data
class User {
    private String name;
    private Integer age;
    private Double salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name)
                && Objects.equals(age, user.age)
                && Objects.equals(salary, user.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, salary);
    }
}
