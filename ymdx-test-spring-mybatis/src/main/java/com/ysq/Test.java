package com.ysq;

import com.ysq.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/8 17:02
 * @Version 1.0
 */
public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = (UserService) applicationContext.getBean("userService");

        userService.test();

    }
}
