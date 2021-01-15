package com.jedis;

import redis.clients.jedis.Jedis;

public class JedisDemo1 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        System.out.println(jedis.ping());
        Long rpush = jedis.rpush("rlistkey", "h", "e", "l", "l", "o");
        System.out.println(jedis.lrange("rlistkey", 0, -1));
        jedis.close();
    }
}
