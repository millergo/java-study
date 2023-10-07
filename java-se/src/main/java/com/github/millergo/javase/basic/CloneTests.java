package com.github.millergo.javase.basic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

/**
 * 关于深拷贝和浅拷贝区别，我这里先给结论：
 * <p>
 * 浅拷贝：浅拷贝会在堆上创建一个新的对象（区别于引用拷贝的一点），
 * 不过，如果原对象内部的属性是引用类型的话，浅拷贝会直接复制内部对象的引用地址，
 * 也就是说拷贝对象和原对象共用同一个内部对象。
 * 深拷贝 ：深拷贝会完全复制整个对象，包括这个对象所包含的内部对象。
 *
 * @author Miller Shan
 */
public class CloneTests {
    @Test
    void test(){
        Person person1 = new Person(new Address("杭州"));
        Person person1Copy = person1.clone();
        // 浅拷贝， true
        System.out.println(person1.getAddress() == person1Copy.getAddress());
        // 深拷贝， false
        System.out.println(person1.getAddress() == person1Copy.getAddress());
    }
}

@AllArgsConstructor
@Getter @Setter
class Person implements Cloneable {
    private Address address;
    public Person clone() {
        try {
            Person person = (Person) super.clone();
            // 深拷贝，开启下面这行的注释
            person.setAddress(person.getAddress().clone());
            return person;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
@AllArgsConstructor
@Data
class Address implements Cloneable {
    private String name;
    public Address clone() {
        try {
            return (Address) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}