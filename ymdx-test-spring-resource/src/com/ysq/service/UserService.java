package com.ysq.service;

import com.ysq.spring.*;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/1 11:06
 * @Version 1.0
 */
@Component
public class UserService implements UserInterface{


    @Autowired
    private OrderService orderService;



    @Override
    public void test(){
        System.out.println(orderService);
    }

}
