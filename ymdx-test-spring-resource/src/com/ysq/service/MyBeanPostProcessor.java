package com.ysq.service;

import com.ysq.spring.BeanPostProcessor;
import com.ysq.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/2 16:43
 * @Version 1.0
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object beanPostBeforeProcessor(String beanName, Object bean) {
        if("userService".equals(beanName)){
            System.out.println("afterPropertiesSet方法前执行");
        }
        return bean;
    }

    @Override
    public Object beanPostAfterProcessor(String beanName, Object bean) {

        if("userService".equals(beanName)){
            Object instance = Proxy.newProxyInstance(MyBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    //代理对象执行的切面逻辑
                    System.out.println("切面逻辑");
                    return method.invoke(bean, args);
                }
            });
            return instance;
        }

        return bean;

    }
}
