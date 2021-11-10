package com.xxcx.admin.controller;


import com.xxcx.admin.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaoShuangQi
 * @description 测试AOP拦截
 * @date 2021/5/15 20:29
 */
@RestController
@Slf4j
public class AspectTestController {

    @GetMapping("/doNormal")
    public String doNormal(String name) {
        log.info("【执行方法】：doNormal");
        return "doNormal";
    }

    @GetMapping("/doWithException")
    public String doWithException(String name, String age, User user) {
        log.info("【执行方法】：doWithException");
        int a = 1 / 0;
        return "doWithException";
    }

}