package com.ymdx.redis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/1/13 10:30
 * @Version 1.0
 */
@Configuration
public class RedisConfiguration {

    /**
     * 构建消息监听容器
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter){
        final RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        //监听 db=6的key为test-key过期事件
        listenerContainer.addMessageListener(listenerAdapter, new PatternTopic("__keyevent@6__:expired=test-key"));
        return listenerContainer;
    }

    /**
     * 注入监听适配器
     * @return
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(){
        return new MessageListenerAdapter(new BusinessHandle(), MessageListenerAdapter.ORIGINAL_DEFAULT_LISTENER_METHOD);
    }
}
