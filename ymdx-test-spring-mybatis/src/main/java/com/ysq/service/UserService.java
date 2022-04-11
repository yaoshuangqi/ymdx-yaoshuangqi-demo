package com.ysq.service;

import com.ysq.mapper.MemberMapper;
import com.ysq.mapper.OrderMapper;
import com.ysq.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/8 17:06
 * @Version 1.0
 */
@Component
public class UserService {

    /**
     * UserMapper代理对象 -> bean  mybatis产生
     */
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private MemberMapper memberMapper;

    public void test(){
        System.out.println(userMapper.getUser());
        System.out.println(orderMapper.getOrder());
        System.out.println(memberMapper.getMember());
    }
}
