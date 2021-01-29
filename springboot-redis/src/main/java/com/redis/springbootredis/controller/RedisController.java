package com.redis.springbootredis.controller;

import com.redis.springbootredis.config.RedisLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class RedisController {
    /** Redis 分布式锁操作类*/
    @Autowired
    private RedisLock redisLock;
    /** 线程池*/
    ExecutorService executor = Executors.newFixedThreadPool(10);

    @GetMapping("getLock")
    public void lockTest(){
        for (int i = 0; i < 5000; i++){
            // 在线程池中，执行带分布式锁的业务逻辑
            executor.submit(() -> {
                executeLockService();
            });
        }
    }

    /**
     * 所以，分布式锁一定要保证，哪个进程加的锁就该由哪个进行进行锁的释放，这里是分布式多实例情况，
     * 所以在执行加锁逻辑时，一定要设置个 UUID 唯一值来充当锁的值，
     * 在解锁时候也带上该值，来保证加锁和解锁的是在同一实例中，从而避免上述情况发生
     */
    private void executeLockService() {
        //设置Redis键，该键名跟业务挂钩，应该为一个不变的值，这里设置为test
        String key = "test";
        // 生成 UUID，将该 UUID 最为 Redis 的值
        //注：设置个UUid 随机充当value是为了保证，在分布式环境下存在多实例的情况。
        //进行枷锁和解锁操作的都是相同的进程（同一个实例），这样能够避免该所别的进程（实例）执行解锁操作
        String value = UUID.randomUUID().toString();
        boolean result = redisLock.tryLock(key, value, 10, TimeUnit.SECONDS);
        if(result){
            log.info("获取分布式锁，执行对应逻辑1");
            log.info("获取分布式锁，执行对应逻辑2");
            log.info("获取分布式锁，执行对应逻辑3");
            redisLock.unLock(key,value);
        }else {
            log.info("获取锁失败");
        }
    }
}
