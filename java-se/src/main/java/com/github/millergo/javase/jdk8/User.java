package com.github.millergo.javase.jdk8;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode
public class User {
    private String name;
    private Integer age;
    private Double salary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) &&
                Objects.equals(age, user.age) &&
                Objects.equals(salary, user.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, salary);
    }
}
