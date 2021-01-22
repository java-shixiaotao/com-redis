package com.redis.springbootredis.demo;

import com.redis.springbootredis.annotation.AnnotationDemo;
import com.redis.springbootredis.annotation.MyAnnotations;

@MyAnnotations({@AnnotationDemo("111"),@AnnotationDemo("222")})
public class SpringBootRedisDemo {
        public static void testParam(String... am){
               for (Object ele : am){
                       System.out.println(ele);
               }
        }


        public static void main(String[] args) {

                testParam("user1","user2");
        }
}
