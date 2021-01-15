package com.jedis;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

public class JedisTransaction {


    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("hello", "world");
        jsonObject.put("name", "sxt");
        jedis.flushDB();
        Transaction multi = jedis.multi();
        String result = jsonObject.toJSONString();
        try {
            multi.set("user1", result);
            //int i = 10 / 0;
            multi.exec();
        } catch (Exception e) {
            multi.discard();
            System.out.println("放弃了事务");
            e.printStackTrace();
        } finally {
            System.out.println(multi.get("user1"));
            System.out.println(jedis.get("user1"));
            jedis.close();
        }
    }
}
