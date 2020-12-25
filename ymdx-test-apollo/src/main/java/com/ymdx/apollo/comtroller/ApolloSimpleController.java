package com.ymdx.apollo.comtroller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YaoShuangQi
 * @version 1.0.0
 * @project ymdx-yaoshuangqi-demo
 * @date 2020/12/18
 * @Description
 */
@RestController
public class ApolloSimpleController {

    @Value("${textName}")
    private String key;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String textReaderApollo(){
        return "根据配置中心获取的值："+key;
    }
}
