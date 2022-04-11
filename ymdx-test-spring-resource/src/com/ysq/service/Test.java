package com.ysq.service;

import com.ysq.spring.YsqApplicationContext;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/1 9:42
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) {

        /**
         * 创建bean的生命周期
         * 通过扫描.class ---> 反射 无参的构造方式(推断构造方法)  --> 对象  --> 依赖注入 --> 初始化前（postConstruct） --> 初始化 --> 初始化后 --> 放入Map(单例池) --> bean对象
         */
        YsqApplicationContext ysqApplicationContext = new YsqApplicationContext(AppConfig.class);

        UserInterface userService = (UserInterface) ysqApplicationContext.getBean("userService");

        userService.test();

        /*System.out.println(ysqApplicationContext.getBean("userService"));
        System.out.println(ysqApplicationContext.getBean("userService"));
        System.out.println(ysqApplicationContext.getBean("userService"));
        System.out.println(ysqApplicationContext.getBean("userService"));*/
    }
}
