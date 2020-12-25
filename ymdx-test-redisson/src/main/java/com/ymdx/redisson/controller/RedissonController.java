package com.ymdx.redisson.controller;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2020/12/24
 * @Description
 */
@RestController
public class RedissonController {

    @Autowired
    private RedissonClient redissonClient;

    @RequestMapping(value = "/test/redisson", method = RequestMethod.GET)
    public String testRedisson(){
        // test 为锁的主键，根据情况可使用 UUID
        RLock rLock = redissonClient.getLock("test");
        System.out.println(Thread.currentThread().getName() + " 进入方法");
        //如果锁已经被使用，则会一直在等着锁被释放
        rLock.lock();
//       占用锁后，10秒后自动释放
//        rLock.lock(10, TimeUnit.SECONDS);
        try {
            System.out.println(Thread.currentThread().getName() + " 开始执行");
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 执行完成，解锁");
            if (rLock.isLocked()) {
                rLock.unlock();
            }
        }
        System.out.println(Thread.currentThread().getName() + " 返回结果");
        return "SUCCESS";
    }
}
