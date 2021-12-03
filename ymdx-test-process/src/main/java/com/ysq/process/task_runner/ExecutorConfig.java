package com.ysq.process.task_runner;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2021/11/10 15:40
 * @Version 1.0
 */
@Configuration(proxyBeanMethods = false)
public class ExecutorConfig {

    @Bean("myExecutor")
    public ThreadPoolTaskExecutor executor(){
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        //服务器核心线程数
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("服务器核心线程数：" + availableProcessors);
        threadPoolTaskExecutor.setCorePoolSize(availableProcessors);
        threadPoolTaskExecutor.setMaxPoolSize(100);
        threadPoolTaskExecutor.setQueueCapacity(10);

        //拒绝策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());


        return threadPoolTaskExecutor;
    }
}
