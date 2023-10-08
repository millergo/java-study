package com.github.millergo.javase.jdk8;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Miller Shan
 * @version 1.0
 * @since 2023/8/14 10:22:02
 */
public class TempTest {
    public static void main(String[] args) {
        List<Person> list1 = new ArrayList<>();
        list1.add(new Person() {{
            setId(1);
            setName("张三");
        }});
        list1.add(new Person() {{
            setId(2);
            setName("李四");
        }});
        list1.add(new Person() {{
            setId(3);
            setName("wang");
        }});

        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(3);
        list2.add(5);
        list2.add(7);

        List<String> list3 = list1.stream().filter(obj -> !list2.contains(obj.getId())).map(obj -> obj.getName()).collect(Collectors.toList());

        System.out.println(list3);
    }
}

@Data
class Person {
    Integer id;
    String name;
}
