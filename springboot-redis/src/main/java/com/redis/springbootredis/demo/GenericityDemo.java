package com.redis.springbootredis.demo;

import com.redis.springbootredis.entity.Person;

public class GenericityDemo {
    public static void main(String[] args) {
        Person<String,String> person = new Person<>();
        person.setName("sxt");
        person.setAddress("ht");
        System.out.println(person);

        person.show(person);
        Person.showOne(22222);

    }
}
