package com.redis.springbootredis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 尝试获取锁
     * @param key
     * @param value
     * @param expireTime
     * @param timeUnit
     * @return
     */
    public boolean tryLock(String key, String value, int expireTime, TimeUnit timeUnit){
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value);
        if (Boolean.TRUE.equals(result)){
            log.info("申请锁（" + key + "|" + value + ")成功");
            return true;
        }
        log.error("申请锁(" + key + "|" + value + ")失败");
        return false;
    }



    public void unLock(String key ,String value){
        // 设置 Lua 脚本，其中 KEYS[1] 是 key，KEYS[2] 是 value  这里的 unLock 方法中需要执行多个操作，所以这里使用 Lua 脚本保证执行的原子性。
        String script = "if redis.call('get', KEYS[1]) == KEYS[2] then return redis.call('del', KEYS[1]) else return 0 end";
        DefaultRedisScript<Long> longDefaultRedisScript = new DefaultRedisScript<>(script, Long.class);
        Long result = redisTemplate.execute(longDefaultRedisScript, Arrays.asList(key, value));
        if (result != null && result != 0L) {
            log.info("解锁(" + key + "|" + value + ")成功");
        } else {
            log.info("解锁(" + key + "|" + value + ")失败！");
        }
    }


}
