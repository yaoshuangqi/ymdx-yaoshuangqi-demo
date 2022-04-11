package com.ysq.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/8 17:02
 * @Version 1.0
 */
public interface UserMapper {

    @Select("select 'user'")
    String getUser();

    @Select("select account_no from count_account")
    String getUserName();
}
