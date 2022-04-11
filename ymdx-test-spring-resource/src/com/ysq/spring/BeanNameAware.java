package com.ysq.spring;

/**
 * @Desc aware的回调机制
 * @Author Mr.Yao
 * @Date 2022/4/2 16:16
 * @Version 1.0
 */
public interface BeanNameAware {

    void setBeanName(String beanName);
}
