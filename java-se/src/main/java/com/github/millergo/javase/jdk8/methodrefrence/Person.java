package com.github.millergo.javase.jdk8.methodrefrence;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Miller Shan
 * @version 1.0
 * @since 2023/10/7 19:48:08
 */
@AllArgsConstructor
@Data
public class Person {
    private String name;
    private Integer age;

    public int compareByAge(Person person) {
        return this.getAge() - person.getAge();
    }

    public int compareByName(Person person) {
        return this.getName().compareToIgnoreCase(person.getName());
    }
}
