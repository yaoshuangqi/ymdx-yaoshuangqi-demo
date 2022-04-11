package com.ysq.spring;

/**
 * @Desc 创建bean的前后处理器
 * @Author Mr.Yao
 * @Date 2022/4/2 16:31
 * @Version 1.0
 */
public interface BeanPostProcessor {

    Object beanPostBeforeProcessor(String beanName, Object bean);

    Object beanPostAfterProcessor(String beanName, Object bean);
}
