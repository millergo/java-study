package com.github.millergo.javase.jdk8.methodrefrence;

import com.github.millergo.javase.jdk8.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;

/**
 * 方法引用实际上是lambada表达式的一种语法糖形式。只是方法引用所引用的方法体刚好有一个现成的方法已经实现了此功能，所以可以直接使用。
 * 方法引用共分为4类：
 * 1. 类名::静态方法名；
 * 2. 引用名(对象名)::实例方法名；
 * 3. 类名::实例方法名；
 * 4. 构造方法引用,类名::new
 */
public class MethodReference {

    @DisplayName("类名::静态方法名")
    @Test
    void testClassMethodName() {
        String[] array = new String[]{"Apple", "Orange", "Banana", "Lemon"};
        /*
        所谓方法引用，是指如果某个方法签名和接口恰好一致，就可以直接传入方法引用。int compare(String, String)
        方法签名只看参数类型和返回类型，不看方法名称，也不看类的继承关系。
         */
        Arrays.sort(array, MethodReference::cmp);

        // 因为实例方法有一个隐含的this参数，String类的compareTo()方法在实际调用的时候，第一个隐含参数总是传入this，相当于静态方法
        // public static int compareTo(this, String o);
        System.out.println(String.join(", ", array));
    }

    static int cmp(String s1, String s2) {
        return s1.compareTo(s2);
    }

    @DisplayName("通过类名::实例方法名")
    @Test
    void testClassInstanceMethod() {
        Person person1 = new Person("Miller", 33);
        Person person2 = new Person("Mila", 7);
        Person person3 = new Person("Vicky", 34);
        Person person4 = new Person("Millie", 2);

        List<Person> personList = Arrays.asList(person1, person2, person3, person4);

        // Collections.sort(personList, (item1, item2) -> item1.getName().compareToIgnoreCase(item2.getName()));
        /*
         * 通过类名::实例方法名调用。下面这行与上面 lambda 表达式相同。
         * compareByName 是一个实例方法，实例方法肯定是由一个对象来调用的。
         * 这个对象就是 lambda 表达式的第一个参数来调用的 compareByName 的 ，
         * 比如：item1.getName().compareToIgnoreCase()。
         * 如果方法接受多个参数，那么后续所有的参数值依次作为参数值传递给第一个参数。
         */
        Collections.sort(personList, Person::compareByName);

        personList.forEach(item -> System.out.println(item.getName()));
        System.out.println("----------------------");
        // 通过类名::实例方法名调用
        List<String> cities = Arrays.asList("HangZhou", "BeJin", "ShangHai", "HuBei");
        // Collections.sort(cities, (city1, city2) -> city1.compareToIgnoreCase(city2));
        // 第一个参数 city1 作为 compareToIgnoreCase 的对象调用实例方法，后面所有参数作为方法的值依次传递
        Collections.sort(cities, String::compareToIgnoreCase);
        cities.forEach(System.out::println);
    }

    public MethodReference() {
        System.out.println("Construct Method invoked.");
    }

    @DisplayName("构造方法引用,类名::new")
    @Test
    void testConstructMethodReference() {
        Runnable aNew = MethodReference::new;
    }
}
@AllArgsConstructor
@Data
class Person {
    private String name;
    private Integer age;

    public int compareByAge(Person person) {
        return this.getAge() - person.getAge();
    }

    public int compareByName(Person person) {
        return this.getName().compareToIgnoreCase(person.getName());
    }
}
