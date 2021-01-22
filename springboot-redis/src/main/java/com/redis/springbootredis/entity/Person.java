package com.redis.springbootredis.entity;


import lombok.Data;

@Data
public class Person<T,K> {
    private T name;
    private K address;

    public <T> void show(T name) {
        System.out.println(name + "正在学习");
    }

    public static <W> void showOne(W name) {
        System.out.println(name + "正在学习");
    }
}
