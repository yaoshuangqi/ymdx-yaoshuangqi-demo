package com.ysq.mapper;

import org.apache.ibatis.annotations.Select;

/**
 * @Desc
 * @Author Mr.Yao
 * @Date 2022/4/8 17:02
 * @Version 1.0
 */
public interface MemberMapper {

    @Select("select 'user'")
    String getMember();

}
