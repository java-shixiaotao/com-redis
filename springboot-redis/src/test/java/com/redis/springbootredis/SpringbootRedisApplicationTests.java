package com.redis.springbootredis;

import com.redis.springbootredis.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
class SpringbootRedisApplicationTests {
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {

        /**
         * 1.redis的基本操作
         * opsForValue 操作字符串
         * opsForList  操作list
         * opsForHash  操作hash
         * opsForSet   操作set
         * opsForZSet  操作zset
         */
        redisTemplate.opsForValue();
        redisTemplate.opsForList();
        redisTemplate.opsForHash();
        redisTemplate.opsForSet();
        redisTemplate.opsForZSet();
        /**
         * 2.
         * 除了基本的操作,我们常用的方法都可以直接通过redisTemplate操作,比如事务,和基本curd.
         */
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.multi();
        redisTemplate.opsForHash().put("hashKey","hk1","hv1");
        redisTemplate.discard();
        /**
         * 3.
         * 获取redis的连接对象
         */
        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        connection.flushDb();
        connection.flushAll();

        redisTemplate.opsForSet().add("k1",1,2,4,5,6);
        System.out.println(redisTemplate.opsForSet().members("k1"));
    }

    @Test
    void test2(){
        User user = new User("史小涛","2");
        redisTemplate.opsForValue().set("user",user);
        System.out.println(redisTemplate.opsForValue().get("user"));
        String s = "";
        s = 5 > 8 ? "111": "112221";
    }

}
